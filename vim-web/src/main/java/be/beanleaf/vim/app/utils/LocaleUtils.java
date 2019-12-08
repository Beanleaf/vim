package be.beanleaf.vim.app.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public abstract class LocaleUtils {

  public static Double getLocalDouble(Locale locale, String input) throws ParseException {
    if (input == null || input.isEmpty()) {
      return null;
    }

    return NumberFormat.getNumberInstance(locale).parse(input).doubleValue();
  }

  public static String getLocalNumberString(Locale locale, Number number) {
    if (number == null) {
      return null;
    }
    return NumberFormat.getNumberInstance(locale).format(number);
  }
}
