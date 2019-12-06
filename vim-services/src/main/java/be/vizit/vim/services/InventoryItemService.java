package be.vizit.vim.services;

import be.vizit.vim.domain.ItemStatus;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.InventoryLog;
import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.domain.entities.User;
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
  private final InventoryLogService inventoryLogService;

  @Autowired
  public InventoryItemService(InventoryitemRepository inventoryitemRepository,
      InventoryLogService inventoryLogService) {
    this.inventoryitemRepository = inventoryitemRepository;
    this.inventoryLogService = inventoryLogService;
  }

  @Transactional(readOnly = true)
  public InventoryItem findByUuidOrId(String input) {
    InventoryItem byUuid = findByUuid(input);
    if (byUuid != null) {
      return byUuid;
    }
    return getInventoryItem(Long.parseLong(input));
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

  @Transactional(readOnly = true)
  public long countAllItems() {
    return inventoryitemRepository.count();
  }

  @Transactional(readOnly = true)
  public long countAllItemsInCategory(ItemCategory itemCategory) {
    return inventoryitemRepository.countAllByItemCategory(itemCategory);
  }

  @Transactional(readOnly = true)
  public long countAllItemsByStatus(ItemStatus status) {
    return inventoryitemRepository.countAllByCurrentStatus(status);
  }

  @Transactional(readOnly = true)
  public long countAllItemsByStatusAndCategory(ItemStatus status, ItemCategory category) {
    return category != null
        ? inventoryitemRepository.countAllByCurrentStatusAndItemCategory(status, category)
        : countAllItemsByStatus(status);
  }

  public InventoryItem createNewItem(ItemCategory itemCategory, String description, boolean active,
      String brand, User user) {
    InventoryItem item = new InventoryItem();
    item.setItemCategory(itemCategory);
    item.setDescription(description);
    item.setActive(active);
    item.setUuid(UUID.randomUUID().toString());
    item.setAddedByUser(user);
    item.setCurrentStatus(ItemStatus.AVAILABLE);
    item.setBrand(brand);
    return item;
  }

  @Transactional
  public void updateItem(InventoryItem item, ItemCategory itemCategory, String description,
      boolean active,
      String brand) {
    item.setItemCategory(itemCategory);
    item.setDescription(description);
    item.setActive(active);
    item.setBrand(brand);
  }

  @Transactional
  public void delete(InventoryItem inventoryitem) {
    List<InventoryLog> logs = inventoryLogService.findForItem(inventoryitem, Pageable.unpaged());
    if (!logs.isEmpty()) {
      inventoryitem.setActive(false);
    } else {
      inventoryitemRepository.delete(inventoryitem);
    }
  }

  @Transactional
  public void saveItemMultipleTimes(Integer amount, ItemCategory category, String description,
      boolean active, String brand, User user) {
    int totalAmount = amount != null ? amount : 1;
    for (int i = 1; i <= totalAmount; i++) {
      String newDescription = totalAmount > 1 ? description + " #" + i : description;
      InventoryItem item = createNewItem(category, newDescription, active, brand, user);
      save(item);
    }
  }

  @Transactional
  void save(InventoryItem inventoryItem) {
    inventoryitemRepository.save(inventoryItem);
  }
}
