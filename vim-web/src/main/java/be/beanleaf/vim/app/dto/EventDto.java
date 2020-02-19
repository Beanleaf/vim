package be.beanleaf.vim.app.dto;

import be.beanleaf.vim.domain.entities.SalesOutlet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EventDto {

  @NotEmpty(message = "{validations.notEmpty.message}")
  private String name;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalTime startTime;
  private LocalTime endTime;
  @NotNull
  private SalesOutlet venue;

  //TODO: BAH BAH BAH
  public EventDto() {
    this(null, null, null, null);
  }

  public EventDto(
      String name,
      LocalDateTime startDate,
      LocalDateTime endDate,
      SalesOutlet venue) {
    setName(name);
    setStartDate(startDate.toLocalDate());
    setStartTime(startDate.toLocalTime());
    setEndDate(endDate.toLocalDate());
    setEndTime(endDate.toLocalTime());
    setVenue(venue);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public SalesOutlet getVenue() {
    return venue;
  }

  public void setVenue(SalesOutlet venue) {
    this.venue = venue;
  }
}
