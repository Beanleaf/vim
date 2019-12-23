package be.beanleaf.vim.app.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class WebUtils {

  private static final String gravatarBaseUrl = "https://www.gravatar.com/avatar/";

  public static String getGravatarImgSrc(String email, int size, String deault) {
    String hash = DigestUtils.md5Hex(email);
    return gravatarBaseUrl + hash + "?d=" + deault + "&s=" + size;
  }

  public static String getGravatarImgSrc(String email, int size) {
    return getGravatarImgSrc(email, size, "blank");
  }

  public static String getExactPatternMatch(String input) {
    if (StringUtils.isEmpty(input)) {
      return "";
    }
    StringBuilder pattern = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
      pattern.append("[").append(input.charAt(i)).append("]");
    }
    return pattern.toString();
  }
}
