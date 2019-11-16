package be.vizit.vim.domain.entities;

import be.vizit.vim.domain.InventoryDirection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inventory_logs")
public class InventoryLog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private InventoryItem inventoryItem;
  @ManyToOne
  private User user;
  private Date timestamp;
  private InventoryDirection inventoryDirection;
  private String comment;

  public InventoryLog() {
  }

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

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
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
}
