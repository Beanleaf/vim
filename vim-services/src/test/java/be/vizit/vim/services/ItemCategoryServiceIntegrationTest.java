package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.fixtures.ItemCategoryFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ItemCategoryServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private ItemCategoryService itemCategoryService;

  @Test
  void getItemCategory() {
    ItemCategory itemCategory = ItemCategoryFixture.newItemCategory("code");
    store(itemCategory);
    assertNotNull(itemCategoryService.getItemCategory(itemCategory.getId()));
  }

  @Test
  void findAllCategories() {
    ItemCategory itemCategory = ItemCategoryFixture.newItemCategory("code1");
    ItemCategory itemCategory1 = ItemCategoryFixture.newItemCategory("code2");
    storeAll(itemCategory, itemCategory1);
    assertThat(itemCategoryService.findAllCategories()).hasSize(2);
  }

  @Test
  void findByShortCode() {
    store(ItemCategoryFixture.newItemCategory("code"));
    assertNotNull(itemCategoryService.findByShortCode("code"));
  }

  @Test
  void save() {
    ItemCategory itemCategory = ItemCategoryFixture.newItemCategory("code");
    itemCategoryService.save(itemCategory);
    assertNotNull(itemCategory.getId());
  }
}
