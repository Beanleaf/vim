package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    assertNull(inventoryItemService.findByUuid("uuid_b"));
    assertEquals("uuid", inventoryItemService.findByUuid("uuid").getUuid());
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
    assertTrue(inventoryItemService.getInventoryItem(item.getId()).isActive());
    inventoryItemService.delete(item);
    assertFalse(item.isActive());
  }

  @Test
  void save() {
    InventoryItem item = InventoryItemFixture.newInventoryItem("uuid");
    inventoryItemService.save(item);
    assertNotNull(item.getId());
  }

  @Test
  void getQrCode() {
    InventoryItem item = createAndStore(InventoryItemFixture.newInventoryItem("uuid"));
    assertThat(inventoryItemService.getQrCode(item)).isNotEmpty();
  }
}
