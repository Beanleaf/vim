package be.vizit.vim.app.dto;

public class ColumnDto {

  private String content;
  private boolean rawHtml;
  private String css;

  public ColumnDto(String content, boolean rawHtml, String css) {
    this.content = content;
    this.rawHtml = rawHtml;
    this.css = css;
  }

  public String getContent() {
    return content;
  }

  public boolean isRawHtml() {
    return rawHtml;
  }

  public String getCss() {
    return css;
  }
}
