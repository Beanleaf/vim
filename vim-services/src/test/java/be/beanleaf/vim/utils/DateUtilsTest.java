package be.beanleaf.vim.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Calendar;
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

  @Test
  void getHour() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 10);
    assertThat(DateUtils.getHour(cal.getTime())).isEqualTo(10);
  }

  @Test
  void getMinute() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MINUTE, 10);
    assertThat(DateUtils.getMinute(cal.getTime())).isEqualTo(10);
  }

  @Test
  void swapTime() {
    Date dateInput = Date.from(Instant.parse("2020-11-30T18:35:24.00Z"));
    Date timeInput = Date.from(Instant.parse("1900-01-01T15:30:24.00Z"));
    assertThat(DateUtils.swapTime(null, timeInput)).isNull();
    assertThat(DateUtils.swapTime(dateInput, null)).isEqualTo(dateInput);
    Date result = DateUtils.swapTime(dateInput, timeInput);
    assertThat(result).hasHourOfDay(15);
    assertThat(result).hasMinute(30);
    assertThat(result).hasYear(2020);
    assertThat(result).hasMonth(11);
    assertThat(result).hasDayOfMonth(30);
  }
}