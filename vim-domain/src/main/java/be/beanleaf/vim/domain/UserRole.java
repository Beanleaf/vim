package be.beanleaf.vim.domain;

public enum UserRole implements IntegerEnum {
  ADMIN(99),
  STANDARD(1);

  private int id;

  UserRole(int id) {
    this.id = id;
  }

  @Override
  public int getId() {
    return id;
  }

}
