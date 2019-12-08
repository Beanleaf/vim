package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

  ItemCategory findItemCategoryById(Long id);

  ItemCategory findByShortCode(String shortCode);

}
