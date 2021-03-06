package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.repository.InventoryitemRepository;
import be.beanleaf.vim.utils.DateUtils;
import be.beanleaf.vim.utils.DbUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class InventoryItemService extends AbstractVimService {

  private final InventoryitemRepository inventoryitemRepository;
  private final InventoryLogService inventoryLogService;

  @Autowired
  public InventoryItemService(InventoryitemRepository inventoryitemRepository,
      InventoryLogService inventoryLogService) {
    this.inventoryitemRepository = inventoryitemRepository;
    this.inventoryLogService = inventoryLogService;
  }

  @Override
  public Sort getDefaultSort() {
    return InventoryItem.DEFAULT_SORT;
  }

  @Transactional(readOnly = true)
  public InventoryItem findByUuidOrId(String input) {
    InventoryItem byUuid = findByUuid(input);
    if (byUuid != null) {
      return byUuid;
    }
    return StringUtils.isNumeric(input) ? getInventoryItem(Long.parseLong(input)) : null;
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
  public List<InventoryItem> findAll(Pageable pageable) {
    return inventoryitemRepository.findAll(pageable).getContent();
  }

  @Transactional(readOnly = true)
  public List<InventoryItem> findItems(String description, ItemCategory cat, ItemStatus status,
      Pageable page) {
    return inventoryitemRepository.findAll(buildSpec(description, cat, status), page).getContent();
  }

  @Transactional
  public long countItems(String description, ItemCategory cat, ItemStatus status) {
    return inventoryitemRepository.count(buildSpec(description, cat, status));
  }

  private Specification<InventoryItem> buildSpec(String descOrBrand, ItemCategory cat,
      ItemStatus status) {
    List<Specification<InventoryItem>> specs = new ArrayList<>();
    if (!StringUtils.isEmpty(descOrBrand)) {
      Specification<InventoryItem> descSpec = (item, cq, cb) -> cb
          .like(cb.upper(item.get("description")), "%" + descOrBrand.toUpperCase() + "%");
      specs.add(descSpec.or((item, cq, cb) -> cb
          .like(cb.upper(item.get("brand")), "%" + descOrBrand.toUpperCase() + "%")));
    }
    if (cat != null) {
      specs.add((item, cq, cb) -> cb.equal(item.get("itemCategory"), cat));
    }
    if (status != null) {
      specs.add((item, cq, cb) -> cb.equal(item.get("currentStatus"), status));
    }

    specs.add((item, cq, cb) -> cb.equal(item.get("isDeleted"), false));
    return DbUtils.combineAnd(specs);
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
    item.setDeleted(false);
    item.setAddedOn(LocalDateTime.now());
    return item;
  }

  @Transactional
  public void updateItem(InventoryItem item, ItemCategory itemCategory, String description,
      boolean active, String brand, Double value, ItemStatus status) {
    item.setItemCategory(itemCategory);
    item.setDescription(description);
    item.setActive(active);
    item.setBrand(brand);
    item.setValue(value);
    item.setCurrentStatus(status);
  }

  @Transactional
  public void delete(InventoryItem inventoryItem) {
    List<InventoryLog> logs = inventoryLogService.findForItem(inventoryItem, Pageable.unpaged());
    if (!CollectionUtils.isEmpty(logs)) {
      inventoryLogService.deleteLogs(logs);
    }
    inventoryitemRepository.delete(inventoryItem);
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
  public Double findValueOnDate(LocalDateTime date) {
    return inventoryitemRepository.findValueOnDate(DateUtils.atEndOfDay(date));
  }

  @Transactional
  public void save(InventoryItem inventoryItem) {
    inventoryitemRepository.save(inventoryItem);
  }

  public String getShortCode(InventoryItem inventoryItem) {
    return StringUtils.join(
        inventoryItem.getItemCategory().getShortCode().toUpperCase(),
        "_",
        inventoryItem.getDescription().toUpperCase().substring(0, 5)
    );
  }

  @Transactional(readOnly = true)
  public List<InventoryItem> findAllActiveItems() {
    return inventoryitemRepository.findAllByActiveTrueOrderByDescriptionAsc();
  }
}
