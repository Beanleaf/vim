package be.vizit.vim.repository;

import be.vizit.vim.domain.ItemStatus;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryitemRepository extends PagingAndSortingRepository<InventoryItem, Long> {

  InventoryItem findByUuid(String uuid);

  InventoryItem findInventoryItemById(Long id);

  List<InventoryItem> findAllByItemCategory(ItemCategory itemCategory, Pageable pageable);

  long countAllByItemCategory(ItemCategory itemCategory);

  long countAllByCurrentStatus(ItemStatus currentStatus);

  long countAllByCurrentStatusAndItemCategory(ItemStatus currentStatus, ItemCategory itemCategory);
}
