package be.vizit.vim.repository;

import be.vizit.vim.domain.entities.InventoryLog;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryLogRepository extends PagingAndSortingRepository<InventoryLog, Long> {

}
