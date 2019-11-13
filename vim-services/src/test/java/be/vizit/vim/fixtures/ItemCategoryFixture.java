package be.vizit.vim.fixtures;

import be.vizit.vim.domain.entities.ItemCategory;

public class ItemCategoryFixture {

  public static ItemCategory newItemCategory(String shortCode) {
    ItemCategory itemCategory = new ItemCategory();
    itemCategory.setShortCode(shortCode);
    return itemCategory;
  }
}
