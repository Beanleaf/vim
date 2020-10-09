package be.beanleaf.vim.domain.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.domain.Sort;

@Entity
@Table(name = "events")
public class Event extends AbstractVimEntity {

  public static final Sort DEFAULT_SORT = Sort
      .by("startTime", "name", "venue." + Venue.DEFAULT_SORT_FIELD);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  @ManyToOne(optional = false)
  private User plannedByUser;
  @ManyToOne(optional = false)
  private Venue venue;
  private boolean deleted;

  @Override
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

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public User getPlannedByUser() {
    return plannedByUser;
  }

  public void setPlannedByUser(User plannedByUser) {
    this.plannedByUser = plannedByUser;
  }

  public Venue getVenue() {
    return venue;
  }

  public void setVenue(Venue venue) {
    this.venue = venue;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
