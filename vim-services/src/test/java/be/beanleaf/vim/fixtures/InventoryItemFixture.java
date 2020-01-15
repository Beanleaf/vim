package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.domain.entities.User;
import java.util.Date;

public abstract class InventoryItemFixture {

  public static InventoryItem newInventoryItem(String uuid, ItemCategory itemCategory, User user, ItemStatus status) {
    InventoryItem inventoryItem = new InventoryItem();
    inventoryItem.setUuid(uuid);
    inventoryItem.setItemCategory(itemCategory);
    inventoryItem.setAddedByUser(user);
    inventoryItem.setCurrentStatus(status);
    inventoryItem.setAddedOn(new Date());
    return inventoryItem;
  }

  public static InventoryItem newInventoryItem(String uuid, ItemCategory itemCategory, User user) {
    return newInventoryItem(uuid, itemCategory, user, ItemStatus.AVAILABLE);
  }

}
