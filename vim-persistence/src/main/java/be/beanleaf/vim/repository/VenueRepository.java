package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.Venue;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VenueRepository extends PagingAndSortingRepository<Venue, Long>,
    JpaSpecificationExecutor<Venue> {

  Venue findById(long id);

  List<Venue> findAllByDeleted(boolean deleted, Pageable pageable);
}
