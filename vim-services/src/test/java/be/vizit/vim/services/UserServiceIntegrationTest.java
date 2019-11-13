package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.domain.UserRole;
import be.vizit.vim.domain.entities.User;
import be.vizit.vim.fixtures.UserFixture;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private UserService userService;

  @Test
  void getUser() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid"));
    assertThat(userService.getUser(user.getId())).isNotNull();
    assertThat(userService.getUserByUsername("bob")).isNotNull();
  }

  @Test
  void deactivateUser() {
    User user = createAndStore(UserFixture.newUser("bob", "uuid", true));
    assertThat(user.isActive()).isTrue();
    userService.deactivateUser(user.getId());
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
  void findUsersByRole() {
    User user = UserFixture.newUser("bob", "uuid");
    user.setUserRole(UserRole.ADMIN);
    store(user);
    User user2 = UserFixture.newUser("tom", "uuid2");
    user2.setUserRole(UserRole.ADMIN);
    store(user2);
    assertThat(userService.findUsersByRole(UserRole.ADMIN).size()).isEqualTo(2);
  }

  @Test
  void getUserByUuid() {
    store(UserFixture.newUser("bob", "uuid"));
    assertThat(userService.getUserByUuid("uuid")).isNotNull();
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
}
