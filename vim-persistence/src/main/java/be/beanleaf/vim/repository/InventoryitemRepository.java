package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.InventoryItem;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryitemRepository extends PagingAndSortingRepository<InventoryItem, Long>,
    JpaSpecificationExecutor<InventoryItem> {

  InventoryItem findByUuid(String uuid);

  InventoryItem findInventoryItemById(Long id);

  @Query("select sum(i.value) from InventoryItem i where i.addedOn <= :date")
  Double findValueOnDate(@Param("date") LocalDateTime date);

  List<InventoryItem> findAllByActiveTrueOrderByDescriptionAsc();
}
