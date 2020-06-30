package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.entities.SalesOutlet;

public abstract class SalesOutletFixture {

  public static SalesOutlet newSalesOutlet(String name) {
    SalesOutlet salesOutlet = new SalesOutlet();
    salesOutlet.setName(name);
    salesOutlet.setDeleted(false);
    return salesOutlet;
  }
}
