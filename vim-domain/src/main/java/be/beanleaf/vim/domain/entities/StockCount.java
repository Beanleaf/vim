package be.beanleaf.vim.domain.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock_counts")
public class StockCount extends AbstractVimEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private LocalDateTime timestamp;
  @ManyToOne(optional = false)
  private User user;
  private Integer crateCount;
  private Integer looseCount;
  private boolean confirmed;
  @ManyToOne
  private Event event;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getCrateCount() {
    return crateCount;
  }

  public void setCrateCount(Integer crateCount) {
    this.crateCount = crateCount;
  }

  public Integer getLooseCount() {
    return looseCount;
  }

  public void setLooseCount(Integer looseCount) {
    this.looseCount = looseCount;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }
}
