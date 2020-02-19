package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.entities.Product;
import be.beanleaf.vim.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService extends AbstractVimService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Sort getDefaultSort() {
    return Sort.by("description");
  }

  @Transactional(readOnly = true)
  public Product getProduct(long id) {
    return productRepository.findProductById(id);
  }

}
