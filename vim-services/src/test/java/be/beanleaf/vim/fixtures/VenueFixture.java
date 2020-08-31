package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.entities.Venue;

public abstract class VenueFixture {

  public static Venue newVenue(String name) {
    Venue venue = new Venue();
    venue.setName(name);
    venue.setDeleted(false);
    return venue;
  }

  public static Venue newVenue() {
    return newVenue("outlet");
  }
}
