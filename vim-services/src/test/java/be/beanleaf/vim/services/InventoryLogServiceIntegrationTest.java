package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.InventoryDirection;
import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.fixtures.InventoryItemFixture;
import be.beanleaf.vim.fixtures.InventoryLogFixture;
import be.beanleaf.vim.fixtures.ItemCategoryFixture;
import be.beanleaf.vim.fixtures.UserFixture;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

class InventoryLogServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  InventoryLogService inventoryLogService;
  @Autowired
  InventoryItemService inventoryItemService;

  @Test
  void getDefaultSort() {
    assertThat(inventoryLogService.getDefaultSort()).isEqualTo(Sort.by(Order.desc("timestamp")));
  }

  @Test
  void getInventoryLog() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", itemCategory, user, ItemStatus.LEND));
    InventoryLog log = InventoryLogFixture.newInventoryLog(item, user);
    createAndStore(log);
    assertThat(inventoryLogService.getInventoryLog(log.getId())).isNotNull();
  }

  @Test
  void logIn() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", itemCategory, user, ItemStatus.LEND));
    inventoryLogService.logIn(item, user);
  }

  @Test
  void logOut() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", itemCategory, user, ItemStatus.AVAILABLE));
    inventoryLogService.logOut(item, user);
  }

  @Test
  void log() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
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
  void findRecentLogsForUser() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    assertThat(inventoryLogService.findRecentLogsForUser(user, InventoryDirection.IN)).isEmpty();
  }

  @Test
  void logDefect() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", itemCategory, user, ItemStatus.LEND));
    InventoryLog log = inventoryLogService
        .log(item, user, InventoryDirection.IN, ItemStatus.AVAILABLE);

    inventoryLogService.logDefect(log, "defect");
    assertThat(log.getComment()).isNotNull();
    assertThat(item.getCurrentStatus()).isEqualTo(ItemStatus.DEFECT);
  }

  private void persistTestLogs() {
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = UserFixture.newUser("bob", "uuid", "mail");
    user.setName("Bob");
    store(user);
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", itemCategory, user, ItemStatus.LEND));
    inventoryLogService.log(item, user, InventoryDirection.IN, ItemStatus.AVAILABLE);
    inventoryLogService.log(item, user, InventoryDirection.OUT, ItemStatus.LEND);
  }

  @Test
  void searchLogs() {
    persistTestLogs();
    List<InventoryLog> searchResult = inventoryLogService
        .searchLogs("%bob%", null, LocalDateTime.now(), Pageable.unpaged());
    assertThat(searchResult.size()).isEqualTo(2);
  }

  @Test
  void countLogs() {
    persistTestLogs();
    LocalDateTime from = LocalDateTime.now().minusDays(1);
    LocalDateTime to = LocalDateTime.now().plusDays(1);
    assertThat(inventoryLogService.countLogs(null, from, to)).isEqualTo(2);
  }

  @Test
  void countLogsByDate() {
    persistTestLogs();
    assertThat(inventoryLogService.countLogsByDate(LocalDateTime.now())).isEqualTo(2);
  }

  @Test
  void getUniqueusers() {
    assertThat(inventoryLogService.getUniqueUsers(Lists.emptyList())).isEmpty();
    ItemCategory itemCategory = createAndStore(ItemCategoryFixture.newItemCategory("code"));
    User user = UserFixture.newUser("bob", "uuid", "mail");
    User user2 = UserFixture.newUser("bob", "uuid", "mail");
    InventoryItem item = InventoryItemFixture
        .newInventoryItem("uuid", itemCategory, user, ItemStatus.LEND);
    assertThat(inventoryLogService.getUniqueUsers(
        Arrays.asList(
            InventoryLogFixture.newInventoryLog(item, user),
            InventoryLogFixture.newInventoryLog(item, user2)
        )
    )).hasSize(2);
  }
}
