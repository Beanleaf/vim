package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.InventoryDirection;
import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.fixtures.InventoryItemFixture;
import be.beanleaf.vim.fixtures.ItemCategoryFixture;
import be.beanleaf.vim.fixtures.UserFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class InventoryLogServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  InventoryLogService inventoryLogService;
  @Autowired
  InventoryItemService inventoryItemService;

  @Test
  void log() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid"));
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", itemCategory, user, ItemStatus.LEND));
    InventoryLog log = inventoryLogService
        .log(item, user, InventoryDirection.IN, ItemStatus.AVAILABLE);
    assertThat(log.getId()).isNotNull();
    assertThat(log.getTimestamp()).isNotNull();
    assertThat(log.getInventoryDirection()).isEqualTo(InventoryDirection.IN);
    assertThat(log.getUser().getUuid()).isEqualTo("uuid");
    assertThat(inventoryItemService.getInventoryItem(item.getId()).getCurrentStatus()).isEqualTo(
        ItemStatus.AVAILABLE);
  }

  @Test
  void logDefect() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid"));
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", itemCategory, user, ItemStatus.LEND));
    InventoryLog log = inventoryLogService
        .log(item, user, InventoryDirection.IN, ItemStatus.AVAILABLE);

    inventoryLogService.logDefect(log, "defect");
    assertThat(log.getComment()).isNotNull();
    assertThat(item.getCurrentStatus()).isEqualTo(ItemStatus.DEFECT);
  }
}
