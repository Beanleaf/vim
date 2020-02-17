package be.beanleaf.vim.domain.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_counts")
public class ProductCount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Date timestamp;
  @ManyToOne(optional = false)
  private User user;
  private Integer crateCount;
  private Integer looseCount;
  private boolean confirmed;
  @ManyToOne
  private Event event;

  public ProductCount() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
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
