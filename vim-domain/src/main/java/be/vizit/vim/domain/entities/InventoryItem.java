package be.vizit.vim.domain.entities;

import be.vizit.vim.domain.ItemStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private boolean active;
  @Column(unique = true, nullable = false)
  private String uuid;
  private String description;
  @ManyToOne(optional = false)
  private ItemCategory itemCategory;
  @ManyToOne(optional = false)
  private User addedByUser;
  @Column(nullable = false)
  private ItemStatus currentStatus;

  public InventoryItem() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ItemCategory getItemCategory() {
    return itemCategory;
  }

  public void setItemCategory(ItemCategory itemCategory) {
    this.itemCategory = itemCategory;
  }

  public User getAddedByUser() {
    return addedByUser;
  }

  public void setAddedByUser(User addedByUser) {
    this.addedByUser = addedByUser;
  }

  public ItemStatus getCurrentStatus() {
    return currentStatus;
  }

  public void setCurrentStatus(ItemStatus currentStatus) {
    this.currentStatus = currentStatus;
  }
}
