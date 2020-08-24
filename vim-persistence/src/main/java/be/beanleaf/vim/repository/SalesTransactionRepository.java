package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.SalesTransaction;
import be.beanleaf.vim.domain.entities.Venue;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SalesTransactionRepository extends
    PagingAndSortingRepository<SalesTransaction, Long>,
    JpaSpecificationExecutor<SalesTransaction> {

  long countAllByOutlet(Venue outlet);
}
