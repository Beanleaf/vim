package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.InventoryItem;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface InventoryitemRepository extends PagingAndSortingRepository<InventoryItem, Long>,
    JpaSpecificationExecutor<InventoryItem> {

  InventoryItem findByUuid(String uuid);

  InventoryItem findInventoryItemById(Long id);

  @Query("select sum(i.value) from InventoryItem i where i.addedOn <= :date")
  Double findValueOnDate(@Param("date") Date date);
}
