package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.ItemCategory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryitemRepository extends PagingAndSortingRepository<InventoryItem, Long> {

  InventoryItem findByUuid(String uuid);

  InventoryItem findInventoryItemById(Long id);

  List<InventoryItem> findAllByItemCategory(ItemCategory itemCategory, Pageable pageable);

  List<InventoryItem> findAllByCurrentStatus(ItemStatus currentSatus, Pageable pageable);

  List<InventoryItem> findAllByItemCategoryAndCurrentStatus(ItemCategory itemCategory,
      ItemStatus currentStatus, Pageable pageable);

  long countAllByItemCategory(ItemCategory itemCategory);

  long countAllByCurrentStatus(ItemStatus currentStatus);

  long countAllByCurrentStatusAndItemCategory(ItemStatus currentStatus, ItemCategory itemCategory);
}
