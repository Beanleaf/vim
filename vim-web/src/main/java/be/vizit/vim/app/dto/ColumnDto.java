package be.vizit.vim.app.dto;

public class ColumnDto {

  private String content;
  private boolean rawHtml;

  public ColumnDto(String content, boolean rawHtml) {
    this.content = content;
    this.rawHtml = rawHtml;
  }

  public String getContent() {
    return content;
  }

  public boolean isRawHtml() {
    return rawHtml;
  }
}
