package be.beanleaf.vim.app.dto;

import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.ItemCategory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class InventoryItemDto implements FormDto {

  @NotNull(message = "{validations.notNull.message}")
  private ItemCategory itemCategory;
  @NotEmpty(message = "{validations.notEmpty.message}")
  private String description;
  private boolean active;
  @Length(max = 100, message = "{validations.length.100}")
  private String brand;
  private Integer amount;
  private ItemStatus status;
  private String value;

  public InventoryItemDto() {
    this(null, null, true, null, null, ItemStatus.AVAILABLE);
  }

  public InventoryItemDto(
      ItemCategory itemCategory, String description, boolean active, String brand, String value,
      ItemStatus status) {
    this.itemCategory = itemCategory;
    this.description = description;
    this.active = active;
    this.brand = brand;
    this.value = value;
    this.status = status;
  }

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

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public ItemStatus getStatus() {
    return status;
  }

  public void setStatus(ItemStatus status) {
    this.status = status;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
