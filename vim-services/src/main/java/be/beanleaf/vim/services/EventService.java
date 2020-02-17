package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.entities.Event;
import be.beanleaf.vim.domain.entities.SalesOutlet;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.repository.EventRepository;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

  private EventRepository eventRepository;

  @Autowired
  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event createNewEvent(String name, Date startTime, Date endTime, User planner,
      SalesOutlet venue) {
    Event event = new Event();
    event.setName(name);
    event.setVenue(venue);
    event.setDeleted(false);
    event.setEndTime(endTime);
    event.setStartTime(startTime);
    event.setPlannedByUser(planner);
    return event;
  }

  @Transactional
  public void updateEvent(Event event, String name, Date startTime, Date endTime,
      SalesOutlet venue) {
    event.setName(name);
    event.setVenue(venue);
    event.setEndTime(endTime);
    event.setStartTime(startTime);
  }

  @Transactional
  public void save(Event event) {
    eventRepository.save(event);
  }

  @Transactional(readOnly = true)
  public List<Event> findAllEvents(boolean includeDeleted, Pageable page) {
    return eventRepository.findAllByDeleted(includeDeleted, page);
  }

  @Transactional(readOnly = true)
  public long countEvents(String q) {
    return eventRepository.count(buildSpec(q));
  }

  private Specification<Event> buildSpec(String name) {
    if (StringUtils.isEmpty(name)) {
      return null; //TODO: or Specification.where(null);
    }
    return (event, cq, cb) -> cb.like(cb.upper(event.get("name")), "%" + name.toUpperCase() + "%");
  }
}
