package be.vizit.vim.fixtures;

import be.vizit.vim.domain.InventoryDirection;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.InventoryLog;
import be.vizit.vim.domain.entities.User;
import java.util.Date;

public abstract class InventoryLogFixture {

  public static InventoryLog newInventoryLog(InventoryItem item, User user) {
    InventoryLog log = new InventoryLog();
    log.setTimestamp(new Date());
    log.setInventoryDirection(InventoryDirection.IN);
    log.setInventoryItem(item);
    log.setUser(user);
    return log;
  }
}
