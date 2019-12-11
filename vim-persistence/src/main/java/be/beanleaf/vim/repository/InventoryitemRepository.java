package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryitemRepository extends PagingAndSortingRepository<InventoryItem, Long>,
    JpaSpecificationExecutor<InventoryItem> {

  InventoryItem findByUuid(String uuid);

  InventoryItem findInventoryItemById(Long id);
}
