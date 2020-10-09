package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.entities.SalesTransaction;
import be.beanleaf.vim.domain.entities.Venue;
import be.beanleaf.vim.repository.SalesTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalesTransactionService extends AbstractVimService {

  private final SalesTransactionRepository salesTransactionRepository;

  @Autowired
  public SalesTransactionService(SalesTransactionRepository salesTransactionRepository) {
    this.salesTransactionRepository = salesTransactionRepository;
  }

  @Override
  public Sort getDefaultSort() {
    return SalesTransaction.DEFAULT_SORT;
  }

  @Transactional(readOnly = true)
  public long countTransactions(Venue venue) {
    return salesTransactionRepository.countAllByOutlet(venue);
  }
}
