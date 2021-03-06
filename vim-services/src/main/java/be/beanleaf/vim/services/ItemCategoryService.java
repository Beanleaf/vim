package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.repository.ItemCategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemCategoryService implements VimService {

  private final ItemCategoryRepository itemCategoryRepository;

  @Autowired
  public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
    this.itemCategoryRepository = itemCategoryRepository;
  }

  @Override
  public Sort getDefaultSort() {
    return ItemCategory.DEFAULT_SORT;
  }

  @Transactional(readOnly = true)
  public ItemCategory getItemCategory(long id) {
    return itemCategoryRepository.findItemCategoryById(id);
  }

  @Transactional(readOnly = true)
  public List<ItemCategory> findAllCategories() {
    return itemCategoryRepository.findAll(getDefaultSort());
  }

  @Transactional(readOnly = true)
  public ItemCategory findByShortCode(String shortCode) {
    return itemCategoryRepository.findByShortCode(shortCode);
  }

  @Transactional
  public void save(ItemCategory itemCategory) {
    itemCategoryRepository.save(itemCategory);
  }
}
