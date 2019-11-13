package be.vizit.vim.fixtures;

import be.vizit.vim.domain.entities.InventoryItem;

public class InventoryItemFixture {

  public static InventoryItem newInventoryItem(String uuid) {
    InventoryItem inventoryItem = new InventoryItem();
    inventoryItem.setUuid(uuid);
    return inventoryItem;
  }
}
