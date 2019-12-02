package be.vizit.vim.domain;

public enum ItemStatus implements IntegerEnum {
  AVAILABLE(0, "available"),
  LEND(1, "lend"),
  DEFECT(9, "defect");

  private int id;
  private String description;

  ItemStatus(int id, String description) {
    this.id = id;
    this.description = description;
  }

  @Override
  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }
}
