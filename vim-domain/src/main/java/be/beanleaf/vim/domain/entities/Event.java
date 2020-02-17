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
@Table(name = "events")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  private Date startTime;
  private Date endTime;
  @ManyToOne(optional = false)
  private User plannedByUser;
  @ManyToOne(optional = false)
  private SalesOutlet venue;
  private boolean deleted;

  public Event() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public User getPlannedByUser() {
    return plannedByUser;
  }

  public void setPlannedByUser(User plannedByUser) {
    this.plannedByUser = plannedByUser;
  }

  public SalesOutlet getVenue() {
    return venue;
  }

  public void setVenue(SalesOutlet venue) {
    this.venue = venue;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
