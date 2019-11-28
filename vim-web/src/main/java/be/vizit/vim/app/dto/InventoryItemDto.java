package be.vizit.vim.app.dto;

import be.vizit.vim.domain.entities.ItemCategory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class InventoryItemDto implements FormDto {
  @NotNull(message = "{validations.notNull.message}")
  private ItemCategory itemCategory;
  @NotEmpty(message = "{validations.notEmpty.message}")
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
