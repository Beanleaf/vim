package be.vizit.vim.fixtures;

import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;

public abstract class InventoryItemFixture {

  public static InventoryItem newInventoryItem(String uuid, ItemCategory itemCategory) {
    InventoryItem inventoryItem = new InventoryItem();
    inventoryItem.setUuid(uuid);
    inventoryItem.setItemCategory(itemCategory);
    return inventoryItem;
  }

  public static InventoryItem newInventoryItem(String uuid) {
    return newInventoryItem(uuid, null);
  }
}
