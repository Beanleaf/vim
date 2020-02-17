package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.InventoryDirection;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryLogRepository extends PagingAndSortingRepository<InventoryLog, Long>,
    JpaSpecificationExecutor<InventoryLog> {

  InventoryLog findInventoryLogById(long id);

  List<InventoryLog> findAllByInventoryItem(InventoryItem inventoryItem, Pageable pageable);

  List<InventoryLog> findAllByUserAndTimestampBetweenAndInventoryDirectionOrderByTimestampDesc(
      User user, Date startDate, Date endDate, InventoryDirection direction);

  List<InventoryLog> findAllByUser(User user, Pageable pageable);

  long countAllByInventoryItem(InventoryItem item);

  long countAllByUser(User user);

  long countAllByTimestampBetween(Date startDate, Date endDate);
}
