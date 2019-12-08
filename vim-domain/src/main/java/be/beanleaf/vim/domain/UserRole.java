package be.beanleaf.vim.domain;

public enum UserRole implements IntegerEnum {
  ADMIN(99, "administrator"),
  STANDARD(1, "standard user");

  private int id;
  private String description;

  UserRole(int id, String description) {
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
