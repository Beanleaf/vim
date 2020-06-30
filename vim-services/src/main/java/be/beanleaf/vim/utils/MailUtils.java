package be.beanleaf.vim.utils;

public final class MailUtils {

  MailUtils() {
  }

  public static boolean isValidEmailAddress(String input) {
    String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    return input.matches(regex);
  }
}
