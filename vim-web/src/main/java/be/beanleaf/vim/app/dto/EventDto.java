package be.beanleaf.vim.app.dto;

import be.beanleaf.vim.domain.entities.SalesOutlet;
import be.beanleaf.vim.utils.DateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EventDto {

  @NotEmpty(message = "{validations.notEmpty.message}")
  private String name;
  //@DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;
  //@DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;
  private Integer startHour;
  private Integer endHour;
  @NotNull(message = "{validations.choice}")
  private SalesOutlet venue;

  public EventDto() {
  }

  public EventDto(
      String name,
      LocalDateTime startDate,
      LocalDateTime endDate,
      SalesOutlet venue) {
    setName(name);
    setStartDate(startDate.toLocalDate());
    setStartHour(startDate.getHour());
    setEndDate(endDate.toLocalDate());
    setEndHour(endDate.getHour());
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

  public SalesOutlet getVenue() {
    return venue;
  }

  public void setVenue(SalesOutlet venue) {
    this.venue = venue;
  }

  public Integer getStartHour() {
    return startHour;
  }

  public void setStartHour(Integer startHour) {
    this.startHour = startHour;
  }

  public Integer getEndHour() {
    return endHour;
  }

  public void setEndHour(Integer endHour) {
    this.endHour = endHour;
  }

  public LocalDateTime getActualStartDate() {
    return DateUtils.setHour(getStartDate(), getStartHour());
  }

  public LocalDateTime getActualEndDate() {
    return DateUtils.setHour(getEndDate(), getEndHour());
  }
}
