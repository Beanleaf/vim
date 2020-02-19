package be.beanleaf.vim.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class DateUtils {

  public static LocalDateTime atEndOfMonth(LocalDateTime date) {
    LocalDateTime localDateTime = atEndOfDay(date);
    return localDateTime.withDayOfMonth(localDateTime.getMonth().maxLength());
  }

  public static LocalDateTime atStartOfDay(LocalDateTime date) {
    return date.with(LocalTime.MIN);
  }

  public static LocalDateTime atEndOfDay(LocalDateTime date) {
    return date.with(LocalTime.MAX);
  }
}
