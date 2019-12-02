package be.vizit.vim.services;

import be.vizit.vim.domain.InventoryDirection;
import be.vizit.vim.domain.ItemStatus;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.InventoryLog;
import be.vizit.vim.domain.entities.User;
import be.vizit.vim.repository.InventoryLogRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryLogService {

  private final InventoryLogRepository inventoryLogRepository;

  @Autowired
  public InventoryLogService(InventoryLogRepository inventoryLogRepository) {
    this.inventoryLogRepository = inventoryLogRepository;
  }

  @Transactional
  public InventoryLog logIn(InventoryItem inventoryItem, User user) {
    InventoryLog log = new InventoryLog();
    log.setInventoryItem(inventoryItem);
    log.setInventoryDirection(InventoryDirection.IN);
    log.setUser(user);
    log.setTimestamp(new Date());
    inventoryItem.setCurrentStatus(ItemStatus.AVAILABLE);
    return inventoryLogRepository.save(log);
  }

  public List<InventoryLog> findForItem(InventoryItem inventoryitem, Pageable pageable) {
    return inventoryLogRepository.findAllByInventoryItem(inventoryitem, pageable);
  }
}
