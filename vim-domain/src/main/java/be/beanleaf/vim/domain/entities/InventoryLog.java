package be.beanleaf.vim.domain.entities;

import be.beanleaf.vim.domain.InventoryDirection;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inventory_logs")
public class InventoryLog extends AbstractVimEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(optional = false)
  private InventoryItem inventoryItem;
  @ManyToOne(optional = false)
  private User user;
  @Column(nullable = false)
  private LocalDateTime timestamp;
  @Column(nullable = false)
  private InventoryDirection inventoryDirection;
  private String comment;
  private boolean defect;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public InventoryItem getInventoryItem() {
    return inventoryItem;
  }

  public void setInventoryItem(InventoryItem inventoryItem) {
    this.inventoryItem = inventoryItem;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public InventoryDirection getInventoryDirection() {
    return inventoryDirection;
  }

  public void setInventoryDirection(InventoryDirection inventoryDirection) {
    this.inventoryDirection = inventoryDirection;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public boolean isDefect() {
    return defect;
  }

  public void setDefect(boolean defect) {
    this.defect = defect;
  }
}
