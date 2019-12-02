package be.vizit.vim.fixtures;

import be.vizit.vim.domain.ItemStatus;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.domain.entities.User;

public abstract class InventoryItemFixture {

  public static InventoryItem newInventoryItem(String uuid, ItemCategory itemCategory, User user, ItemStatus status) {
    InventoryItem inventoryItem = new InventoryItem();
    inventoryItem.setUuid(uuid);
    inventoryItem.setItemCategory(itemCategory);
    inventoryItem.setAddedByUser(user);
    inventoryItem.setCurrentStatus(status);
    return inventoryItem;
  }

  public static InventoryItem newInventoryItem(String uuid, ItemCategory itemCategory, User user) {
    return newInventoryItem(uuid, itemCategory, user, ItemStatus.AVAILABLE);
  }

}
