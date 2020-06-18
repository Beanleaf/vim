package be.beanleaf.vim.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DateUtils {

  private DateUtils() {
  }

  public static LocalDateTime atEndOfMonth(LocalDateTime date) {
    LocalDateTime localDateTime = atEndOfDay(date);
    return localDateTime.withDayOfMonth(localDateTime.getMonth().maxLength());
  }

  public static LocalDateTime atStartOfDay(LocalDateTime date) {
    LocalDate asDate = date.toLocalDate();
    return asDate.atStartOfDay();
  }

  public static LocalDateTime atEndOfDay(LocalDateTime date) {
    return date.with(LocalTime.MAX);
  }

  public static LocalDateTime setHour(LocalDate date, Integer hour) {
    return hour != null ? date.atTime(hour, 0) : date.atStartOfDay();
  }
}
