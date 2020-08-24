package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.fixtures.VenueFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

class VenueServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private VenueService venueService;

  @Test
  void getDefaultSort() {
    assertThat(venueService.getDefaultSort()).isEqualTo(Sort.by("name"));
  }

  @Test
  void findAll() {
    store(VenueFixture.newVenue("outlet 1"));
    store(VenueFixture.newVenue("outlet 2"));
    assertThat(venueService.findAll(false)).hasSize(2);
    assertThat(venueService.findAll(true)).isEmpty();
  }
}