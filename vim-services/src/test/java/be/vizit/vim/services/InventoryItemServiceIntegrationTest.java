package be.vizit.vim.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.vizit.vim.domain.entities.InventoryItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class InventoryItemServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  InventoryItemService inventoryItemService;

  @Test
  void findByUuid() {
    InventoryItem item = new InventoryItem();
    item.setUuid("uuid");
    store(item);
    assertNull(inventoryItemService.findByUuid("uuid_b"));
    assertEquals("uuid", inventoryItemService.findByUuid("uuid").getUuid());
  }

  @Test
  void delete() {
    InventoryItem item = new InventoryItem();
    item.setActive(true);
    store(item);
    assertTrue(inventoryItemService.getInventoryItem(item.getId()).isActive());
    inventoryItemService.delete(item);
    assertFalse(item.isActive());
  }
}
