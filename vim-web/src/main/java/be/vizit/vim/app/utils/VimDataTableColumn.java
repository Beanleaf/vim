package be.vizit.vim.app.utils;

public abstract class VimDataTableColumn<T> {

  private String title;
  private boolean rawHtml;
  private String css;

  public VimDataTableColumn(String title) {
    this(title, false);
  }

  public VimDataTableColumn(String title, boolean rawHtml) {
    this(title, null, rawHtml);
  }

  public VimDataTableColumn(String title, String css) {
    this(title, css, false);
  }

  public VimDataTableColumn(String title, String css, boolean rawHtml) {
    this.title = title;
    this.css = css;
    this.rawHtml = rawHtml;
  }

  public boolean isRawHtml() {
    return rawHtml;
  }

  protected String getText(T object) {
    final Object value = getValue(object);
    if (value == null) {
      return null;
    }

    return value.toString();
  }

  protected Object getValue(T object) {
    return null;
  }

  public String getTitle() {
    return title;
  }

  public String getCss() {
    return css;
  }
}
