package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.domain.entities.User;
import be.vizit.vim.fixtures.UserFixture;
import javax.mail.internet.InternetAddress;
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
  void login() {
    store(UserFixture.newUser("bob", "uuid", true));
    assertThat(userService.login("tom")).isNull();
    assertThat(userService.login("bob")).isNotNull();
    assertThat(userService.login("uuid")).isNotNull();
    store(UserFixture.newUser("tom", "uuid2", false));
    assertThat(userService.login("tom")).isNull();
  }

  @Test
  void getInternetAddress() {
    User user = UserFixture.newUser("bob", "uuid");
    user.setEmailAddress("bob@comp.com");
    InternetAddress internetAddressForUser = userService.getInternetAddress(user);
    assertThat(internetAddressForUser.getPersonal()).isEqualTo("bob");
    assertThat(internetAddressForUser.getAddress()).isEqualTo("bob@comp.com");
  }
}
