package be.vizit.vim.app.utils;

public abstract class WebUtils {

  public static String icon(String css) {
    return "<i class='" + css + "'><i>";
  }

  public static String link(String href, String css, String content) {
    return link(href, css, content, "_self");
  }

  public static String link(String href, String css, String content, String target) {
    return "<a class='" + css + "' href='" + href + "' target='" + target + "'>" + content + "</a>";
  }
}
