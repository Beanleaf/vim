package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.entities.Event;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.domain.entities.Venue;
import be.beanleaf.vim.fixtures.EventFixture;
import be.beanleaf.vim.fixtures.UserFixture;
import be.beanleaf.vim.fixtures.VenueFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class EventServiceIntegrationTest extends ServiceIntegrationTest {

  private final LocalDateTime now = LocalDateTime.now();
  private final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
  @Autowired
  private EventService eventService;

  @Test
  void getDefaultSort() {
    assertThat(eventService.getDefaultSort())
        .isEqualTo(Sort.by("startTime", "name", "venue.name"));
  }

  private Event createAndStoreTestEvent() {
    Venue venue = createAndStore(VenueFixture.newVenue("venue"));
    User planner = createAndStore(UserFixture.newUser("bob", "123", "bob@comp.com"));
    return createAndStore(EventFixture.newEvent(venue, planner));
  }

  @Test
  void getEvent() {
    Event event = createAndStoreTestEvent();
    assertThat(eventService.getEvent(event.getId())).isNotNull();
  }

  @Test
  void updateEvent() {
    Event event = createAndStoreTestEvent();
    eventService
        .updateEvent(event, "event 2", event.getStartTime(), event.getEndTime(), event.getVenue());
  }

  @Test
  void findAllEvents() {
    Event event1 = createAndStoreTestEvent();
    Event event2 = EventFixture
        .newEvent("event2", true, now, tomorrow, event1.getVenue(), event1.getPlannedByUser());
    store(event2);
    assertThat(eventService.findAllEvents(true, Pageable.unpaged())).hasSize(1);
    assertThat(eventService.findAllEvents(false, Pageable.unpaged())).hasSize(1);
  }

  @Test
  void countCurrentOrFutureEvents() {
    Event event1 = createAndStoreTestEvent();
    Venue venue = event1.getVenue();
    Event event2 = EventFixture.newEvent("event2", false, now.minusDays(2), now.minusDays(1),
        venue, event1.getPlannedByUser());
    store(event2);
    assertThat(eventService.countCurrentOrFutureEvents(venue)).isEqualTo(1);
  }


}
