package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.domain.InventoryDirection;
import be.vizit.vim.domain.entities.InventoryLog;
import be.vizit.vim.fixtures.InventoryItemFixture;
import be.vizit.vim.fixtures.UserFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class InventoryLogServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  InventoryLogService inventoryLogService;

  @Test
  void log() {
    InventoryLog log = inventoryLogService.log(
        createAndStore(InventoryItemFixture.newInventoryItem("uuid")),
        InventoryDirection.IN,
        createAndStore(UserFixture.newUser("bob", "uuid")));
    assertThat(log.getId()).isNotNull();
    assertThat(log.getTimestamp()).isNotNull();
    assertThat(log.getInventoryDirection()).isEqualTo(InventoryDirection.IN);
    assertThat(log.getUser().getUuid()).isEqualTo("uuid");
  }
}
