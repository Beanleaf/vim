package be.beanleaf.vim.app.dto;

import be.beanleaf.vim.domain.entities.SalesOutlet;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public class EventDto {

  @NotEmpty(message = "{validations.notEmpty.message}")
  private String name;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endDate;
  @DateTimeFormat(pattern = "HH:mm")
  private Date startTime;
  @DateTimeFormat(pattern = "HH:mm")
  private Date endTime;
  @NotNull
  private SalesOutlet venue;

  public EventDto() {
    this(null, null, null, null);
  }

  public EventDto(
      String name,
      Date startDate,
      Date endDate,
      SalesOutlet venue) {
    setName(name);
    setStartDate(startDate);
    setStartTime(startDate);
    setEndDate(endDate);
    setEndTime(endDate);
    setVenue(venue);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
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
