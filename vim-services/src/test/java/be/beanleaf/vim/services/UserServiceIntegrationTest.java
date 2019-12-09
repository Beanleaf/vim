package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.fixtures.InventoryItemFixture;
import be.beanleaf.vim.fixtures.InventoryLogFixture;
import be.beanleaf.vim.fixtures.ItemCategoryFixture;
import be.beanleaf.vim.fixtures.UserFixture;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private UserService userService;

  @Test
  void deactivateUser() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", true));
    assertThat(user.isActive()).isTrue();
    userService.deactivateUser(user);
    assertThat(user.isActive()).isFalse();
  }

  @Test
  void changePassword() {
    User user = UserFixture.newUser("bob", "uuid");
    byte[] byte16 = new byte[16];
    user.setPasswordSalt(byte16);
    byte[] byte8 = new byte[8];
    user.setPasswordHash(byte8);
    store(user);
    userService.changePassword(user.getId(), Pair.of(new byte[1], new byte[2]));
    assertThat(user.getPasswordHash()).isEqualTo(new byte[1]);
    assertThat(user.getPasswordSalt()).isEqualTo(new byte[2]);
  }

  @Test
  void login() {
    store(UserFixture.newUser("bob", "uuid", true));
    assertThat(userService.login("tom")).isNull();
    assertThat(userService.login("bob")).isNotNull();
    assertThat(userService.login("uuid")).isNotNull();
    store(UserFixture.newUser("tom", "uuid2", false));
    assertThat(userService.login("tom")).isNull();
  }

  @Test
  void delete() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", true));
    userService.delete(user);
    assertThat(userService.getUser(user.getId()).isActive()).isFalse();
    User user1 = createAndStore(UserFixture.newUser("babs", "uuid2", false));
    userService.delete(user1);
    assertThat(userService.getUser(user1.getId())).isNull();
    User user2 = createAndStore(UserFixture.newUser("bobette", "uuid3", true));
    ItemCategory category = createAndStore(ItemCategoryFixture.newItemCategory("CAT"));
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", category, user2, ItemStatus.AVAILABLE));
    store(InventoryLogFixture.newInventoryLog(item, user2));
    userService.delete(user2);
    assertThat(userService.getUser(user2.getId()).isActive()).isFalse();
  }
}
