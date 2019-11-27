package be.vizit.vim.services;

import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.repository.InventoryitemRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public List<InventoryItem> findAllByItemCategory(ItemCategory itemCategory, Pageable pageable) {
    return inventoryitemRepository.findAllByItemCategory(itemCategory, pageable);
  }

  @Transactional(readOnly = true)
  public List<InventoryItem> findAll(Pageable pageable) {
    Page<InventoryItem> all = inventoryitemRepository.findAll(pageable);
    return all.getContent();
  }

  @Transactional
  public InventoryItem createNewItem(ItemCategory itemCategory, String description, boolean active) {
    InventoryItem item = new InventoryItem();
    item.setItemCategory(itemCategory);
    item.setDescription(description);
    item.setActive(active);
    item.setUuid(UUID.randomUUID().toString());
    save(item);
    return item;
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
