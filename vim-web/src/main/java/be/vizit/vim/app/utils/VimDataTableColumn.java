package be.vizit.vim.app.utils;

public abstract class VimDataTableColumn<T> {

  private String title;
  private boolean rawHtml;

  public VimDataTableColumn(String title) {
    this.title = title;
    this.rawHtml = false;
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
}
