package be.vizit.vim.app.dto;

public class DefectDto implements FormDto {

  String comment;

  public DefectDto() {
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
