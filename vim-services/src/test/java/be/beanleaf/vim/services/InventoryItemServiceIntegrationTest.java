package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.fixtures.InventoryItemFixture;
import be.beanleaf.vim.fixtures.InventoryLogFixture;
import be.beanleaf.vim.fixtures.ItemCategoryFixture;
import be.beanleaf.vim.fixtures.UserFixture;
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
  void findAll() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    createAndStore(InventoryItemFixture.newInventoryItem("uuid", itemCategory, user));
    createAndStore(InventoryItemFixture.newInventoryItem("uuid2", itemCategory, user));
    createAndStore(InventoryItemFixture.newInventoryItem("uuid3", itemCategory, user));
    assertThat(inventoryItemService.findAll(Pageable.unpaged())).hasSize(3);
    List<InventoryItem> byUuid = inventoryItemService
        .findAll(PageRequest.of(0, 1, Sort.by("uuid").descending()));
    assertThat(byUuid.get(0).getUuid()).isEqualTo("uuid3");
  }

  @Test
  void findItems() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    InventoryItem item = InventoryItemFixture.newInventoryItem("uuid", itemCategory, user);
    item.setDescription("someDescription");
    store(item);
    createAndStore(InventoryItemFixture.newInventoryItem("uuid2", itemCategory, user));
    createAndStore(InventoryItemFixture.newInventoryItem("uuid3", itemCategory, user));
    List<InventoryItem> result = inventoryItemService
        .findItems(null, null, null, Pageable.unpaged());
    assertThat(result.size()).isEqualTo(3);
    assertThat(inventoryItemService.findItems(null, itemCategory, null, Pageable.unpaged()).size())
        .isEqualTo(3);
    assertThat(
        inventoryItemService.findItems(null, null, ItemStatus.AVAILABLE, Pageable.unpaged()).size())
        .isEqualTo(3);
    assertThat(
        inventoryItemService.findItems(null, null, ItemStatus.LEND, Pageable.unpaged()).size())
        .isEqualTo(0);
    assertThat(inventoryItemService.findItems("descr", null, null, Pageable.unpaged()).size())
        .isEqualTo(1);
  }

  @Test
  void saveItemMultipleTimes() {
    inventoryItemService
        .saveItemMultipleTimes(
            2,
            createAndStore(ItemCategoryFixture.newItemCategory("code")),
            "description",
            false,
            null,
            createAndStore(UserFixture.newUser("bob", "uuid", "mail")));

    List<InventoryItem> items = inventoryItemService.findAll(Pageable.unpaged());
    assertThat(items).hasSize(2);
    assertThat(items.get(0).getDescription()).contains("#");
  }

  @Test
  void delete() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    ItemCategory category = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    InventoryItem item = InventoryItemFixture.newInventoryItem("uuid", category, user);
    item.setActive(true);
    store(item);
    createAndStore(InventoryLogFixture.newInventoryLog(item, user));
    inventoryItemService.delete(item);
    assertThat(item.isActive()).isFalse();

    InventoryItem item2 = createAndStore(InventoryItemFixture.newInventoryItem("uuid2", category, user));
    inventoryItemService.delete(item2);
    assertThat(inventoryItemService.findByUuid("uuid2")).isNull();
  }

}
