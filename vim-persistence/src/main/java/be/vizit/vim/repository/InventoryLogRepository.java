package be.vizit.vim.repository;

import be.vizit.vim.domain.InventoryDirection;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.InventoryLog;
import be.vizit.vim.domain.entities.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryLogRepository extends PagingAndSortingRepository<InventoryLog, Long> {

  List<InventoryLog> findAllByInventoryItem(InventoryItem inventoryItem, Pageable pageable);

  List<InventoryLog> findAllByUserAndTimestampBetweenAndInventoryDirectionOrderByTimestampDesc(
      User user, Date startDate, Date endDate, InventoryDirection direction);
}
