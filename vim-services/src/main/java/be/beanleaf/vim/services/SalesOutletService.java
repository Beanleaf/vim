package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.entities.SalesOutlet;
import be.beanleaf.vim.repository.SalesOutletRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalesOutletService {

  private final SalesOutletRepository salesOutletRepository;

  @Autowired
  public SalesOutletService(SalesOutletRepository salesOutletRepository) {
    this.salesOutletRepository = salesOutletRepository;
  }

  @Transactional(readOnly = true)
  public List<SalesOutlet> findAll(boolean deleted) {
    return salesOutletRepository.findAllByDeleted(deleted);
  }
}
