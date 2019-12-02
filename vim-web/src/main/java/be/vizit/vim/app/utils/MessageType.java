package be.vizit.vim.app.utils;

public enum MessageType {
  ERROR("error", "issue-opened"),
  WARNING("warn", "alert"),
  INFO("info", "info"),
  SUCCESS("success", "check");

  private String cssClass;
  private String iconClass;

  MessageType(String cssClass, String iconClass) {
    this.cssClass = cssClass;
    this.iconClass = iconClass;
  }

  public String getCssClass() {
    return cssClass;
  }

  public String getIconClass() {
    return iconClass;
  }
}
