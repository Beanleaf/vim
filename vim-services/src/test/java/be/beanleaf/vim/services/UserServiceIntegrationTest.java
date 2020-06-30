package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import be.beanleaf.vim.domain.ItemStatus;
import be.beanleaf.vim.domain.UserRole;
import be.beanleaf.vim.domain.entities.InventoryItem;
import be.beanleaf.vim.domain.entities.ItemCategory;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.domain.utilities.ValidationException;
import be.beanleaf.vim.fixtures.InventoryItemFixture;
import be.beanleaf.vim.fixtures.InventoryLogFixture;
import be.beanleaf.vim.fixtures.ItemCategoryFixture;
import be.beanleaf.vim.fixtures.UserFixture;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

class UserServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private UserService userService;

  @Test
  void getDefaultSort() {
    assertThat(userService.getDefaultSort())
        .isEqualTo(Sort.by(Order.asc("name"), Order.asc("name")));
  }

  @Test
  void getUser() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail", true));
    assertThat(userService.getUser(user.getId())).isNotNull();
  }

  @Test
  void getUserByUsername() {
    store(UserFixture.newUser("bob", "uuid", "mail", true));
    assertThat(userService.getUserByUsername("bob")).isNotNull();
    assertThat(userService.getUserByUsername("tom")).isNull();
  }

  @Test
  void getUserByUuid() {
    store(UserFixture.newUser("bob", "uuid", "mail", true));
    assertThat(userService.getUserByUuid("uuid")).isNotNull();
    assertThat(userService.getUserByUuid("uuid2")).isNull();
  }

  @Test
  void findActiveUsersByRole() {
    User user = UserFixture.newUser("bob", "uuid", "mail", true);
    user.setUserRole(UserRole.STAFF);
    store(user);
    assertThat(userService.findActiveUsersByRole(UserRole.ADMIN)).isEmpty();
    assertThat(userService.findActiveUsersByRole(UserRole.STAFF)).hasSize(1);
  }

  @Test
  void findUserByEmailAddress() {
    store(UserFixture.newUser("bob", "uuid", "mail"));
    assertThat(userService.findUserByEmailAddress("mail")).isNotNull();
    assertThat(userService.findUserByEmailAddress("mail2")).isNull();
  }

  @Test
  void findUsers() {
    store(UserFixture.newUser("bob", "uuid", "mail"));
    assertThat(userService.findUsers("bob", false, null, Pageable.unpaged())).hasSize(1);
    assertThat(userService.findUsers("tom", false, null, Pageable.unpaged())).isEmpty();
  }

  @Test
  void countUsers() {
    store(UserFixture.newUser("bob", "uuid", "mail"));
    store(UserFixture.newUser("tom", "uuid2", "mail2", true));
    assertThat(userService.countUsers(null, true, null)).isEqualTo(1);
    assertThat(userService.countUsers(null, false, null)).isEqualTo(1);
    assertThat(userService.countUsers("tom", true, null)).isEqualTo(1);
    assertThat(userService.countUsers("o", true, null)).isEqualTo(1);
    assertThat(userService.countUsers("o", false, null)).isEqualTo(1);
  }

  @Test
  void deactivateUser() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail", true));
    assertThat(user.isActive()).isTrue();
    userService.deactivateUser(user);
    assertThat(user.isActive()).isFalse();
  }

  @Test
  void changePassword() {
    User user = UserFixture.newUser("bob", "uuid", "mail");
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
  void updateUser() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    userService.updateUser(user, "newMail@test.com", true, "newName",
        "0123", UserRole.STANDARD, "tom", "en");
    assertThat(user.getEmailAddress()).isEqualTo("newMail@test.com");
    assertThat(user.isActive()).isTrue();
    assertThat(user.getName()).isEqualTo("newName");
    assertThat(user.getPhonenumber()).isEqualTo("0123");
    assertThat(user.getUserRole()).isEqualTo(UserRole.STANDARD);
    assertThat(user.getLanguageTag()).isEqualTo("en");
  }

  @Test
  void updateEmail() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail"));
    userService.updateEmail(user, "mail2@test.com");
    assertThat(user.getEmailAddress()).isEqualTo("mail2@test.com");
    User user2 = createAndStore(UserFixture.newUser("bobbette", "uuid2", "mail3"));
    assertThatThrownBy(() -> userService.updateEmail(user2, "mail2@test.com"))
        .isInstanceOf(ValidationException.class)
        .hasMessage("error.email.exists");
    assertThatThrownBy(() -> userService.updateEmail(user2, "inalidmail@test..com"))
        .isInstanceOf(ValidationException.class)
        .hasMessage("error.email.invalid");
  }

  @Test
  void login() {
    store(UserFixture.newUser("bob", "uuid", "mail", true));
    assertThat(userService.login("tom")).isNull();
    assertThat(userService.login("bob")).isNotNull();
    assertThat(userService.login("uuid")).isNotNull();
    store(UserFixture.newUser("tom", "uuid2", "mail2", false));
    assertThat(userService.login("tom")).isNull();
  }

  @Test
  void delete() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", "mail", true));
    assertThatThrownBy(() -> userService.delete(user)).isInstanceOf(ValidationException.class)
        .hasMessage("error.user.notDeletable");
    User user1 = createAndStore(UserFixture.newUser("babs", "uuid2", "mail2", false));
    userService.delete(user1);
    assertThat(userService.getUser(user1.getId())).isNull();
    User user2 = createAndStore(UserFixture.newUser("bobette", "uuid3", "mail3", true));
    ItemCategory category = createAndStore(ItemCategoryFixture.newItemCategory("CAT"));
    InventoryItem item = createAndStore(
        InventoryItemFixture.newInventoryItem("uuid", category, user2, ItemStatus.AVAILABLE));
    store(InventoryLogFixture.newInventoryLog(item, user2));
    assertThatThrownBy(() -> userService.delete(user2)).isInstanceOf(ValidationException.class)
        .hasMessage("error.user.notDeletable");
  }
}
