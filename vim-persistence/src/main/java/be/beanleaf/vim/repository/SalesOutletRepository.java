package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.SalesOutlet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SalesOutletRepository extends PagingAndSortingRepository<SalesOutlet, Long>,
    JpaSpecificationExecutor<SalesOutlet> {

  List<SalesOutlet> findAllByDeleted(boolean deleted);
}
