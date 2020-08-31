package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.entities.Venue;
import be.beanleaf.vim.repository.VenueRepository;
import be.beanleaf.vim.utils.DbUtils;
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
public class VenueService extends AbstractVimService {

  private final VenueRepository venueRepository;
  private final EventService eventService;
  private final SalesTransactionService salesTransactionService;

  @Autowired
  public VenueService(VenueRepository venueRepository,
      EventService eventService,
      SalesTransactionService salesTransactionService) {
    this.venueRepository = venueRepository;
    this.eventService = eventService;
    this.salesTransactionService = salesTransactionService;
  }

  @Transactional(readOnly = true)
  public List<Venue> findAll(boolean deleted) {
    return findAll(deleted, null);
  }

  @Transactional(readOnly = true)
  public List<Venue> findAll(boolean deleted, Pageable page) {
    return venueRepository.findAllByDeleted(deleted, page);
  }

  @Transactional(readOnly = true)
  public List<Venue> findVenues(String q, boolean deleted, Pageable page) {
    return venueRepository.findAll(buildSpec(q, deleted), page).getContent();
  }

  @Override
  public Sort getDefaultSort() {
    return Venue.DEFAULT_SORT;
  }

  public Venue createNewVenue(String name, String description) {
    Venue venue = new Venue();
    venue.setDeleted(false);
    venue.setDescription(description);
    venue.setName(name);
    return venue;
  }

  @Transactional
  public void updateVenue(Venue venue, String name, String description) {
    venue.setName(name);
    venue.setDescription(description);
  }

  /**
   * Deletes the sales outlet logically if transactions were made or events are still linked
   * otherwise it will get deleted physically
   *
   * @param venue The outlet to delete
   */
  @Transactional
  public void delete(Venue venue) {
    if (!isDeletable(venue)) {
      return;
    }

    if (salesTransactionService.countTransactions(venue) > 0) {
      venue.setDeleted(true);
      return;
    }

    venueRepository.delete(venue);
  }

  @Transactional
  public void delete(long id) {
    delete(getVenue(id));
  }

  @Transactional(readOnly = true)
  public boolean isDeletable(Venue venue) {
    return eventService.countCurrentOrFutureEvents(venue) <= 0;
  }

  @Transactional(readOnly = true)
  public long countVenues(String q) {
    return venueRepository.count(buildSpec(q, null));
  }

  private Specification<Venue> buildSpec(String q, Boolean deleted) {
    List<Specification<Venue>> specs = new ArrayList<>();
    if (!StringUtils.isEmpty(q)) {
      String searchString = "%" + q.toUpperCase() + "%";
      Specification<Venue> qSpec = (venue, cq, cb) -> cb
          .like(cb.upper(venue.get("name")), searchString);
      qSpec = qSpec
          .or((venue, cq, cb) -> cb.like(cb.upper(venue.get("description")), searchString));
      specs.add(qSpec);
    }
    if (deleted != null) {
      specs.add((venue, cq, cb) -> cb.equal(venue.get("deleted"), deleted));
    }

    return DbUtils.combineAnd(specs);
  }

  @Transactional
  public void save(Venue venue) {
    venueRepository.save(venue);
  }

  @Transactional(readOnly = true)
  public Venue getVenue(long id) {
    return venueRepository.findById(id);
  }
}
