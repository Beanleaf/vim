package be.beanleaf.vim.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MailUtilsTest {

  @Test
  void isValidEmailAddress() {
    assertThat(MailUtils.isValidEmailAddress("test@mail.com")).isTrue();
    assertThat(MailUtils.isValidEmailAddress("test+test@mail.com")).isTrue();
    assertThat(MailUtils.isValidEmailAddress("test.something@mail.mail")).isTrue();
    assertThat(MailUtils.isValidEmailAddress("test@mail")).isFalse();
  }
}