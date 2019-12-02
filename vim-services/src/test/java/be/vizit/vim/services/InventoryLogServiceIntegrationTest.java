package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.domain.InventoryDirection;
import be.vizit.vim.domain.ItemStatus;
import be.vizit.vim.domain.entities.InventoryItem;
import be.vizit.vim.domain.entities.InventoryLog;
import be.vizit.vim.domain.entities.ItemCategory;
import be.vizit.vim.domain.entities.User;
import be.vizit.vim.fixtures.InventoryItemFixture;
import be.vizit.vim.fixtures.ItemCategoryFixture;
import be.vizit.vim.fixtures.UserFixture;
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
}
