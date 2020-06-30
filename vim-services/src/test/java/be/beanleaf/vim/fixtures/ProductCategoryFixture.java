package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.entities.ProductCategory;

public abstract class ProductCategoryFixture {

  public static ProductCategory newProductCategory(String description) {
    ProductCategory category = new ProductCategory();
    category.setDescription(description);
    return category;
  }
}
