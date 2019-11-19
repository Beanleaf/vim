package be.vizit.vim.services;

import be.vizit.vim.domain.InventoryDirection;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.InventoryLog;
import be.vizit.vim.domain.entities.User;
import be.vizit.vim.repository.InventoryLogRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
  public InventoryLog log(InventoryItem inventoryItem, InventoryDirection direction, User user) {
    InventoryLog log = new InventoryLog();
    log.setInventoryItem(inventoryItem);
    log.setInventoryDirection(direction);
    log.setUser(user);
    log.setTimestamp(new Date());
    return inventoryLogRepository.save(log);
  }

  @Transactional(readOnly = true)
  public List<InventoryLog> findAll(Pageable pageable) {
    Page<InventoryLog> all = inventoryLogRepository.findAll(pageable);
    return all.getContent();
  }
}
