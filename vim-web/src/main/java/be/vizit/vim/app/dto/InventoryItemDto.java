package be.vizit.vim.app.dto;

import be.vizit.vim.domain.entities.ItemCategory;

public class InventoryItemDto {
  private ItemCategory itemCategory;
  private String description;
  private boolean active = true;

  public ItemCategory getItemCategory() {
    return itemCategory;
  }

  public void setItemCategory(ItemCategory itemCategory) {
    this.itemCategory = itemCategory;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
