package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.entities.Product;
import be.beanleaf.vim.domain.entities.ProductCategory;

public abstract class ProductFixture {

  public static Product newProduct(String description, Double price, String name,
      ProductCategory category) {
    Product product = new Product();
    product.setDescription(description);
    product.setWholesalePrice(price);
    product.setName(name);
    product.setProductCategory(category);
    return product;
  }
}
