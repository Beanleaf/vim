package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.entities.Product;
import be.beanleaf.vim.domain.entities.ProductCategory;
import be.beanleaf.vim.fixtures.ProductCategoryFixture;
import be.beanleaf.vim.fixtures.ProductFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

class ProductServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private ProductService productService;

  @Test
  void getDefaultSort() {
    assertThat(productService.getDefaultSort()).isEqualTo(Sort.by("description"));
  }

  @Test
  void getProduct() {
    ProductCategory category = createAndStore(
        ProductCategoryFixture.newProductCategory("category"));
    Product product = ProductFixture.newProduct("desc", 1.5, "name", category);
    createAndStore(product);
    assertThat(productService.getProduct(product.getId())).isNotNull();
  }
}