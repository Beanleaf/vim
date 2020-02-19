package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.InventoryDirection;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.User;
import java.time.LocalDateTime;

public abstract class InventoryLogFixture {

  public static InventoryLog newInventoryLog(InventoryItem item, User user) {
    InventoryLog log = new InventoryLog();
    log.setTimestamp(LocalDateTime.now());
    log.setInventoryDirection(InventoryDirection.IN);
    log.setInventoryItem(item);
    log.setUser(user);
    return log;
  }
}
