package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.fixtures.InventoryItemFixture;
import be.vizit.vim.fixtures.ItemCategoryFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    assertThat(inventoryItemService.findAllByItemCategory(itemCategory)).hasSize(2);
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

  @Test
  void getQrCode() {
    InventoryItem item = createAndStore(InventoryItemFixture.newInventoryItem("uuid"));
    assertThat(inventoryItemService.getQrCode(item)).isNotEmpty();
  }
}
