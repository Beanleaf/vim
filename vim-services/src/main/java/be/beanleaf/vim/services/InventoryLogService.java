package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.InventoryDirection;
import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.repository.InventoryLogRepository;
import java.time.Duration;
import java.time.Instant;
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

  @Transactional(readOnly = true)
  public InventoryLog getInventoryLog(long id) {
    return inventoryLogRepository.findInventoryLogById(id);
  }

  @Transactional
  public void logIn(InventoryItem inventoryItem, User user) {
    log(inventoryItem, user, InventoryDirection.IN, ItemStatus.AVAILABLE);
  }

  @Transactional
  public void logOut(InventoryItem inventoryItem, User user) {
    log(inventoryItem, user, InventoryDirection.OUT, ItemStatus.LEND);
  }

  @Transactional
  InventoryLog log(InventoryItem inventoryItem, User user, InventoryDirection direction,
      ItemStatus status) {
    InventoryLog log = new InventoryLog();
    log.setInventoryItem(inventoryItem);
    log.setInventoryDirection(direction);
    log.setUser(user);
    log.setTimestamp(new Date());
    inventoryItem.setCurrentStatus(status);
    return inventoryLogRepository.save(log);
  }

  @Transactional(readOnly = true)
  public List<InventoryLog> findForItem(InventoryItem inventoryitem, Pageable pageable) {
    return inventoryLogRepository.findAllByInventoryItem(inventoryitem, pageable);
  }

  @Transactional(readOnly = true)
  public List<InventoryLog> findRecentLogsForUser(User user, InventoryDirection direction) {
    Instant now = Instant.now();
    Instant yesterday = now.minus(Duration.ofHours(24));
    return inventoryLogRepository
        .findAllByUserAndTimestampBetweenAndInventoryDirectionOrderByTimestampDesc(
            user, Date.from(yesterday), Date.from(now), direction
        );
  }

  @Transactional
  public void logDefect(InventoryLog log, String comment) {
    log.setComment(comment);
    log.getInventoryItem().setCurrentStatus(ItemStatus.DEFECT);
  }
}
