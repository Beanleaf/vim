package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    User user = UserFixture.newUser("bob", "uuid");
    store(user);
    assertNotNull(userService.getUser(user.getId()));
    assertNotNull(userService.getUserByUsername("bob"));
  }

  @Test
  void deactivateUser() {
    User user = UserFixture.newUser("bob", "uuid", true);
    store(user);
    assertTrue(user.isActive());
    userService.deactivateUser(user.getId());
    assertFalse(user.isActive());
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
  void findUserByUuid() {
    User user = UserFixture.newUser("bob", "uuid");
    store(user);
    assertNotNull(userService.findUserByUuid("uuid"));
  }
}
