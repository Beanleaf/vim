package be.vizit.vim.services;

import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.repository.InventoryitemRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryItemService {

  private final InventoryitemRepository inventoryitemRepository;

  @Autowired
  public InventoryItemService(InventoryitemRepository inventoryitemRepository) {
    this.inventoryitemRepository = inventoryitemRepository;
  }

  @Transactional(readOnly = true)
  public InventoryItem findByUuid(String uuid) {
    return inventoryitemRepository.findByUuid(uuid);
  }

  @Transactional(readOnly = true)
  public InventoryItem getInventoryItem(long id) {
    return inventoryitemRepository.findInventoryItemById(id);
  }

  @Transactional(readOnly = true)
  public List<InventoryItem> findAllByItemCategory(ItemCategory itemCategory) {
    return inventoryitemRepository.findAllByItemCategory(itemCategory);
  }

  @Transactional
  public void delete(InventoryItem inventoryitem) {
    inventoryitem.setActive(false);
  }

  @Transactional
  public void save(InventoryItem inventoryItem) {
    inventoryitemRepository.save(inventoryItem);
  }
}
