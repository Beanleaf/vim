package be.beanleaf.vim.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  void atEndOfMonth() {
    LocalDateTime currentDate = LocalDateTime.now();
    LocalDateTime endOfMonth = DateUtils.atEndOfMonth(currentDate);
    assertThat(endOfMonth.getHour()).isEqualTo(23);
    assertThat(endOfMonth.getMinute()).isEqualTo(59);
    assertThat(endOfMonth.getSecond()).isEqualTo(59);
    assertThat(endOfMonth.getMonth()).isEqualTo(currentDate.getMonth());
    assertThat(endOfMonth.getYear()).isEqualTo(currentDate.getYear());
  }

  @Test
  void atStartOfDay() {
    LocalDateTime startOfDay = DateUtils.atStartOfDay(LocalDateTime.now());
    assertThat(startOfDay.getHour()).isEqualTo(0);
    assertThat(startOfDay.getMinute()).isEqualTo(0);
    assertThat(startOfDay.getSecond()).isEqualTo(0);
  }

  @Test
  void atEndOfDay() {
    LocalDateTime endOfDay = DateUtils.atEndOfDay(LocalDateTime.now());
    assertThat(endOfDay.getHour()).isEqualTo(23);
    assertThat(endOfDay.getMinute()).isEqualTo(59);
    assertThat(endOfDay.getSecond()).isEqualTo(59);
  }
}