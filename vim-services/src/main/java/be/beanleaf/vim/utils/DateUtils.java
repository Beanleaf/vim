package be.beanleaf.vim.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtils {

  public static Date atEndOfMonth(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(atEndOfDay(date));
    LocalDateTime endOfMonth = localDateTime
        .withDayOfMonth(localDateTime.getMonth().maxLength());
    return localDateTimeToDate(endOfMonth);
  }

  public static Date atStartOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
    return localDateTimeToDate(startOfDay);
  }

  public static Date atEndOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return localDateTimeToDate(endOfDay);
  }

  private static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  public static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static int getHour(Date date) {
    return dateToLocalDateTime(date).getHour();
  }

  public static int getMinute(Date date) {
    return dateToLocalDateTime(date).getMinute();
  }

  public static Date swapTime(Date dateInput, Date timeInput) {
    if (dateInput == null) {
      return null;
    }

    if (timeInput == null) {
      return dateInput;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateInput);
    cal.set(Calendar.HOUR_OF_DAY, getHour(timeInput));
    cal.set(Calendar.MINUTE, getMinute(timeInput));
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }
}
