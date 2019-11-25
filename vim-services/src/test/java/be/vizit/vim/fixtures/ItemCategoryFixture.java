package be.vizit.vim.fixtures;

import be.vizit.vim.domain.entities.ItemCategory;

public abstract class ItemCategoryFixture {

  public static ItemCategory newItemCategory(String shortCode, String description) {
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setShortCode(shortCode);
    itemCategory.setDescription(description);
    return itemCategory;
  }

  public static ItemCategory newItemCategory(String shortCode) {
    return newItemCategory(shortCode, null);
  }
}
