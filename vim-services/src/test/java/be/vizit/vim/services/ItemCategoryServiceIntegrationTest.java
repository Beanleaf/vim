package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.fixtures.ItemCategoryFixture;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ItemCategoryServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private ItemCategoryService itemCategoryService;

  @Test
  void getItemCategory() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    assertThat(itemCategoryService.getItemCategory(itemCategory.getId())).isNotNull();
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
    store(ItemCategoryFixture.newItemCategory("code"));
    assertThat(itemCategoryService.findByShortCode("code")).isNotNull();
  }

  @Test
  void save() {
    ItemCategory itemCategory = ItemCategoryFixture.newItemCategory("code");
    itemCategoryService.save(itemCategory);
    assertThat(itemCategory.getId()).isNotNull();
  }
}
