package be.beanleaf.vim.app.utils;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class WebUtils {

  private static final String gravatarBaseUrl = "https://www.gravatar.com/avatar/";

  public static String getGravatarImgSrc(String email, int size) {
    String hash = DigestUtils.md5Hex(email);
    return gravatarBaseUrl + hash + "?d=blank&s=" + size;
  }
}
