package be.beanleaf.vim.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  void atEndOfMonth() {
    Date currentDate = new Date();
    Date endOfMonth = DateUtils.atEndOfMonth(currentDate);
    assertThat(endOfMonth).hasHourOfDay(23);
    assertThat(endOfMonth).hasMinute(59);
    assertThat(endOfMonth).hasSecond(59);
    assertThat(endOfMonth).isInSameMonthAs(currentDate);
    assertThat(endOfMonth).isInSameYearAs(currentDate);
  }

  @Test
  void atStartOfDay() {
    Date startOfDay = DateUtils.atStartOfDay(new Date());
    assertThat(startOfDay).hasHourOfDay(0);
    assertThat(startOfDay).hasMinute(0);
    assertThat(startOfDay).hasSecond(0);
  }

  @Test
  void atEndOfDay() {
    Date endOfDay = DateUtils.atEndOfDay(new Date());
    assertThat(endOfDay).hasHourOfDay(23);
    assertThat(endOfDay).hasMinute(59);
    assertThat(endOfDay).hasSecond(59);
  }
}