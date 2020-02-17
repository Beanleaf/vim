package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.Event;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long>,
    JpaSpecificationExecutor<Event> {

  List<Event> findAllByDeleted(boolean deleted, Pageable pageable);
}
