package be.beanleaf.vim.domain;

public enum PaymentType implements IntegerEnum {
  CASH(1),
  CARD(2),
  OTHER(99);

  private int id;

  PaymentType(int id) {
    this.id = id;
  }

  @Override
  public int getId() {
    return id;
  }
}
