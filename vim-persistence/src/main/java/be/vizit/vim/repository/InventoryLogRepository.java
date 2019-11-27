package be.vizit.vim.repository;

import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.InventoryLog;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryLogRepository extends PagingAndSortingRepository<InventoryLog, Long> {

  List<InventoryLog> findAllByInventoryItem(InventoryItem inventoryItem, Pageable pageable);
}
