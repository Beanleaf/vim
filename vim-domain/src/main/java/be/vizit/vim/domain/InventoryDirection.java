package be.vizit.vim.domain;

public enum InventoryDirection implements IntegerEnum {
  OUT(0, "out"),
  IN(1, "in");

  private int id;
  private String description;

  InventoryDirection(int id, String description) {
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
