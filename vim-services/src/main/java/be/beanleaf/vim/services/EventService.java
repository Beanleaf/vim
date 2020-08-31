package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.entities.Event;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.domain.entities.Venue;
import be.beanleaf.vim.repository.EventRepository;
import be.beanleaf.vim.utils.DbUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class EventService extends AbstractVimService {

  private final EventRepository eventRepository;

  @Autowired
  public EventService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public Sort getDefaultSort() {
    return Event.DEFAULT_SORT;
  }

  @Transactional(readOnly = true)
  public Event getEvent(long id) {
    return eventRepository.findById(id);
  }

  public Event createNewEvent(String name, LocalDateTime startTime, LocalDateTime endTime,
      User planner,
      Venue venue) {
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
  public void updateEvent(Event event, String name, LocalDateTime startTime, LocalDateTime endTime,
      Venue venue) {
    event.setName(name);
    event.setVenue(venue);
    event.setEndTime(endTime);
    event.setStartTime(startTime);
  }

  @Transactional
  public void delete(long id) {
    delete(getEvent(id));
  }

  @Transactional
  public void delete(Event event) {
    eventRepository.delete(event);
  }

  @Transactional
  public void save(Event event) {
    eventRepository.save(event);
  }

  @Transactional(readOnly = true)
  public List<Event> findAllEvents(boolean deleted, Pageable page) {
    return eventRepository.findAllByDeleted(deleted, page);
  }

  @Transactional(readOnly = true)
  public long countCurrentOrFutureEvents(Venue outlet) {
    return eventRepository.count(buildSpec(null, outlet, false));
  }

  @Transactional(readOnly = true)
  public long countEvents(String q) {
    return eventRepository.count(buildSpec(q, null, null));
  }

  private Specification<Event> buildSpec(String name, Venue venue, Boolean hasFinished) {

    List<Specification<Event>> specs = new ArrayList<>();
    if (!StringUtils.isEmpty(name)) {
      specs.add(
          (event, query, builder) -> builder.like(
              builder.upper(event.get("name")), "%" + name.toUpperCase() + "%"
          )
      );
    }
    if (venue != null) {
      specs.add(Specification.where(
          (event, query, builder) -> builder.equal(event.get("venue"), venue)
      ));
    }
    if (hasFinished == Boolean.TRUE) {
      specs.add(Specification.where(
          (event, query, builder) -> builder.lessThan(event.get("endTime"), LocalDateTime.now())
      ));
    }
    if (hasFinished == Boolean.FALSE) {
      specs.add(Specification.where(
          (event, query, builder) -> builder
              .greaterThan(event.get("endTime"), LocalDateTime.now())
      ));
    }
    return DbUtils.combineAnd(specs);
  }

}
