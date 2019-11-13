package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.AbstractTest;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PasswordServiceTest extends AbstractTest {

  @Autowired
  private PasswordService passwordService;

  @Test
  void createSaltHashPair() {
    String password = "thereIsNoSpoon";
    Pair<byte[], byte[]> saltHashPair = passwordService.createHashSaltPair(password);
    assertThat(saltHashPair.getLeft()).isNotNull();
    assertThat(saltHashPair.getRight()).isNotNull();
  }

  @Test
  void isExpectedPassword() {
    String password = "thereIsNoSpoon";
    Pair<byte[], byte[]> saltHashPair = passwordService.createHashSaltPair(password);
    byte[] salt = saltHashPair.getLeft();
    byte[] hash = saltHashPair.getRight();
    assertThat(passwordService.isExpectedPassword(password, salt, hash)).isTrue();
    assertThat(passwordService.isExpectedPassword("itIsAFork", salt, hash)).isFalse();
  }

  @Test
  void generateRandomPassword() {
    String password = passwordService.generateRandomPassword(10);
    assertThat(password.length()).isEqualTo(10);
    assertThat(password).doesNotContainAnyWhitespaces();
  }
}
