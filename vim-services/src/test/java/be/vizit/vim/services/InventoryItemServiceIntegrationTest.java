package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.fixtures.InventoryItemFixture;
import be.vizit.vim.fixtures.ItemCategoryFixture;
import be.vizit.vim.fixtures.UserFixture;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class InventoryItemServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  InventoryItemService inventoryItemService;

  @Test
  void findByUuid() {
    store(InventoryItemFixture.newInventoryItem("uuid"));
    assertThat(inventoryItemService.findByUuid("uuid_b")).isNull();
    assertThat(inventoryItemService.findByUuid("uuid").getUuid()).isEqualTo("uuid");
  }

  @Test
  void findAllByItemCategory() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    store(InventoryItemFixture.newInventoryItem("uuid", itemCategory));
    store(InventoryItemFixture.newInventoryItem("uuid2", itemCategory));
    assertThat(inventoryItemService.findAllByItemCategory(itemCategory, Pageable.unpaged()))
        .hasSize(2);
  }

  @Test
  void findAll() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    createAndStore(InventoryItemFixture.newInventoryItem("uuid", itemCategory));
    createAndStore(InventoryItemFixture.newInventoryItem("uuid2", itemCategory));
    createAndStore(InventoryItemFixture.newInventoryItem("uuid3", itemCategory));
    assertThat(inventoryItemService.findAll(Pageable.unpaged())).hasSize(3);
    List<InventoryItem> byUuid = inventoryItemService
        .findAll(PageRequest.of(0, 1, Sort.by("uuid").descending()));
    assertThat(byUuid.get(0).getUuid()).isEqualTo("uuid3");
  }

  @Test
  void createNewItem() {
    InventoryItem newItem = inventoryItemService
        .createNewItem(ItemCategoryFixture.newItemCategory("code"), "description", false,
            createAndStore(UserFixture.newUser("bob", "uuid")));
    assertThat(newItem.getId()).isNotNull();
    assertThat(newItem.getUuid()).isNotNull();
  }

  @Test
  void delete() {
    InventoryItem item = InventoryItemFixture.newInventoryItem("uuid");
    item.setActive(true);
    store(item);
    assertThat(inventoryItemService.getInventoryItem(item.getId()).isActive()).isTrue();
    inventoryItemService.delete(item);
    assertThat(item.isActive()).isFalse();
  }

  @Test
  void save() {
    InventoryItem item = InventoryItemFixture.newInventoryItem("uuid");
    inventoryItemService.save(item);
    assertThat(item.getId()).isNotNull();
  }

}
