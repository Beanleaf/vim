package be.vizit.vim.repository;

import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryitemRepository extends JpaRepository<InventoryItem, Long> {

  InventoryItem findByUuid(String uuid);

  InventoryItem findInventoryItemById(Long id);

  List<InventoryItem> findAllByItemCategory(ItemCategory itemCategory);
}
