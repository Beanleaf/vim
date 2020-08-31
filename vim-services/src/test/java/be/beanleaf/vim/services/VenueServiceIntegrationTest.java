package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.entities.Venue;
import be.beanleaf.vim.fixtures.EventFixture;
import be.beanleaf.vim.fixtures.SalesTransactionFixture;
import be.beanleaf.vim.fixtures.UserFixture;
import be.beanleaf.vim.fixtures.VenueFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class VenueServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private VenueService venueService;

  private void createTestVenues() {
    store(VenueFixture.newVenue("outlet 1"));
    store(VenueFixture.newVenue("outlet 2"));
  }

  @Test
  void getDefaultSort() {
    assertThat(venueService.getDefaultSort()).isEqualTo(Sort.by("name"));
  }

  @Test
  void findAll() {
    createTestVenues();
    assertThat(venueService.findAll(false)).hasSize(2);
    assertThat(venueService.findAll(true)).isEmpty();
  }

  @Test
  void findVenues() {
    createTestVenues();
    assertThat(venueService.findVenues("out", false, Pageable.unpaged())).hasSize(2);
    assertThat(venueService.findVenues("out", true, Pageable.unpaged())).isEmpty();
    assertThat(venueService.findVenues("int", false, Pageable.unpaged())).isEmpty();
  }

  @Test
  void countVenues() {
    createTestVenues();
    assertThat(venueService.countVenues("outlet")).isEqualTo(2);
    assertThat(venueService.countVenues("unexisting")).isZero();
  }

  @Test
  void updateEvent() {
    Venue venue = VenueFixture.newVenue();
    store(venue);
    venueService.updateVenue(venue, "outlet 1.1", "some outlet");
  }

  @Test
  void isDeletable() {
    Venue venue = createAndStore(VenueFixture.newVenue());
    store(EventFixture.newEvent(
        venue,
        createAndStore(UserFixture.newUser("bob", "123", "bob@comp.com"))
    ));
    assertThat(venueService.isDeletable(venue)).isFalse();
    Venue venue2 = createAndStore(VenueFixture.newVenue());
    assertThat(venueService.isDeletable(venue2)).isTrue();
  }

  @Test
  void delete() {
    Venue venue = createAndStore(VenueFixture.newVenue());
    venueService.delete(venue);
    Venue venue2 = createAndStore(VenueFixture.newVenue());
    store(SalesTransactionFixture.newSalesTransaction(venue2));
    venueService.delete(venue2);
    assertThat(venue2.isDeleted());
  }

  @Test
  void getVenue() {
    Venue venue = createAndStore(VenueFixture.newVenue());
    assertThat(venueService.getVenue(venue.getId())).isNotNull();
  }
}