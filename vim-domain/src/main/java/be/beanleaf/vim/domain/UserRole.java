package be.beanleaf.vim.domain;

public enum UserRole implements IntegerEnum {
  ADMIN(99),
  STAFF(3),
  VOLUNTEER(2),
  STANDARD(1);

  private final int id;

  UserRole(int id) {
    this.id = id;
  }

  @Override
  public int getId() {
    return id;
  }
}
