package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.InventoryDirection;
import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.repository.InventoryLogRepository;
import be.beanleaf.vim.utils.DateUtils;
import be.beanleaf.vim.utils.DbUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryLogService extends AbstractVimService {

  private final InventoryLogRepository inventoryLogRepository;

  @Autowired
  public InventoryLogService(InventoryLogRepository inventoryLogRepository) {
    this.inventoryLogRepository = inventoryLogRepository;
  }

  @Override
  public Sort getDefaultSort() {
    return Sort.by(Order.desc("timestamp"));
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
    log.setTimestamp(LocalDateTime.now());
    log.setDefect(false);
    inventoryItem.setCurrentStatus(status);
    return inventoryLogRepository.save(log);
  }

  @Transactional(readOnly = true)
  public List<InventoryLog> findForItem(InventoryItem inventoryitem, Pageable pageable) {
    return inventoryLogRepository.findAllByInventoryItem(inventoryitem, pageable);
  }

  @Transactional(readOnly = true)
  public List<InventoryLog> findRecentLogsForUser(User user, InventoryDirection direction) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime yesterday = now.minusHours(24);
    return inventoryLogRepository
        .findAllByUserAndTimestampBetweenAndInventoryDirectionOrderByTimestampDesc(
            user, yesterday, now, direction
        );
  }

  @Transactional
  public void logDefect(InventoryLog log, String comment) {
    log.setDefect(true);
    log.setComment(comment);
    log.getInventoryItem().setCurrentStatus(ItemStatus.DEFECT);
  }

  @Transactional(readOnly = true)
  public List<InventoryLog> findAllLogsForUser(User user) {
    return inventoryLogRepository.findAllByUser(user, Pageable.unpaged());
  }

  @Transactional
  public void deleteLogs(List<InventoryLog> logs) {
    inventoryLogRepository.deleteAll(logs);
  }

  @Transactional(readOnly = true)
  public long countLogsByDate(LocalDateTime date) {
    return inventoryLogRepository
        .countAllByTimestampBetween(DateUtils.atStartOfDay(date), DateUtils.atEndOfDay(date));
  }

  @Transactional(readOnly = true)
  public long countLogs(String search, LocalDateTime from, LocalDateTime to) {
    return inventoryLogRepository.count(buildSearchSpecification(search, from, to));
  }

  @Transactional(readOnly = true)
  public List<InventoryLog> searchLogs(String search, LocalDateTime from, LocalDateTime to,
      Pageable page) {
    return inventoryLogRepository.findAll(buildSearchSpecification(search, from, to), page)
        .getContent();
  }

  private Specification<InventoryLog> buildSearchSpecification(String search, LocalDateTime from,
      LocalDateTime to) {
    List<Specification<InventoryLog>> specs = new ArrayList<>();
    if (!StringUtils.isEmpty(search)) {
      String upperCased = search.toUpperCase();
      specs.add(Specification.where(
          (root, query, builder) -> {
            final Join<InventoryLog, User> users = root.join("user");
            final Join<InventoryLog, InventoryItem> items = root.join("inventoryItem");
            return builder.or(
                builder.like(builder.upper(users.get("name")), upperCased),
                builder.like(builder.upper(items.get("description")), upperCased)
            );
          }));
    }

    if (from != null) {
      specs.add(Specification.where(
          (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("timestamp"), from)));
    }
    if (to != null) {
      specs.add(Specification.where(
          (root, query, builder) -> builder.lessThanOrEqualTo(root.get("timestamp"), to)));
    }
    return DbUtils.combineAnd(specs);
  }

  public List<User> getUniqueUsers(List<InventoryLog> logs) {
    List<User> users = new ArrayList<>();
    for (InventoryLog log : logs) {
      User user = log.getUser();
      if (!users.contains(user)) {
        users.add(user);
      }
    }
    return users;
  }
}
