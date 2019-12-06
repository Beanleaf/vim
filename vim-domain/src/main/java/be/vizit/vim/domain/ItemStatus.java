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

  public static ItemStatus forId(int id) {
    for (ItemStatus value : values()) {
      if (value.getId() == id) {
        return value;
      }
    }
    throw new IllegalArgumentException("No ItemStatus found for id = " + id);
  }

  public String getDescription() {
    return description;
  }
}
