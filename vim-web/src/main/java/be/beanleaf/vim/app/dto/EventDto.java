package be.beanleaf.vim.app.dto;

import be.beanleaf.vim.domain.entities.SalesOutlet;
import java.util.Date;
import javax.validation.constraints.NotEmpty;

public class EventDto {

  @NotEmpty(message = "{validations.notEmpty.message}")
  private String name;
  private Date startTime;
  private Date endTime;
  @NotEmpty(message = "{validations.notEmpty.message}")
  private SalesOutlet venue;

  public EventDto() {
    this(null, null, null, null);
  }

  public EventDto(
      String name,
      Date startTime,
      Date endTime,
      SalesOutlet venue) {
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.venue = venue;
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

  public SalesOutlet getVenue() {
    return venue;
  }

  public void setVenue(SalesOutlet venue) {
    this.venue = venue;
  }
}
