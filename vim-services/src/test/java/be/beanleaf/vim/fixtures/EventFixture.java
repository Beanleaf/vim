package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.entities.Event;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.domain.entities.Venue;
import java.time.LocalDateTime;

public abstract class EventFixture {

  public static Event newEvent(String name, boolean isDeleted, LocalDateTime startTime,
      LocalDateTime endTime, Venue venue, User planner) {
    Event event = new Event();
    event.setName(name);
    event.setDeleted(isDeleted);
    event.setEndTime(endTime);
    event.setStartTime(startTime);
    event.setVenue(venue);
    event.setPlannedByUser(planner);
    return event;
  }

  public static Event newEvent(Venue venue, User planner) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime tomorrow = now.plusDays(1);
    return newEvent("event", false, now, tomorrow, venue, planner);
  }
}
