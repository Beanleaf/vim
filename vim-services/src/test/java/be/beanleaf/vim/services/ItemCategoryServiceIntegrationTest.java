package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.fixtures.ItemCategoryFixture;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

class ItemCategoryServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private ItemCategoryService itemCategoryService;

  @Test
  void getDefaultSort() {
    assertThat(itemCategoryService.getDefaultSort()).isEqualTo(Sort.by("description"));
  }

  @Test
  void getItemCategory() {
    ItemCategory category = createAndStore(
        ItemCategoryFixture.newItemCategory("code1", "z_description"));
    assertThat(itemCategoryService.getItemCategory(category.getId())).isNotNull();
  }

  @Test
  void findAllCategories() {
    store(ItemCategoryFixture.newItemCategory("code1", "z_description"));
    store(ItemCategoryFixture.newItemCategory("code2", "a_description"));
    List<ItemCategory> allCategories = itemCategoryService.findAllCategories();
    assertThat(allCategories.get(0).getDescription()).isEqualTo("a_description");
    assertThat(allCategories).hasSize(2);
  }

  @Test
  void findByShortCode() {
    store(ItemCategoryFixture.newItemCategory("code1", "z_description"));
    assertThat(itemCategoryService.findByShortCode("wrong")).isNull();
    assertThat(itemCategoryService.findByShortCode("code1")).isNotNull();
  }

  @Test
  void save() {
    itemCategoryService.save(ItemCategoryFixture.newItemCategory("code1", "z_description"));
  }
}
