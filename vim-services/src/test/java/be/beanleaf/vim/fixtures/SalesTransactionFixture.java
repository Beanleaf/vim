package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.entities.SalesTransaction;
import be.beanleaf.vim.domain.entities.Venue;
import java.time.LocalDateTime;

public abstract class SalesTransactionFixture {

  public static SalesTransaction newSalesTransaction(Venue venue) {
    SalesTransaction transaction = new SalesTransaction();
    transaction.setTimestamp(LocalDateTime.now());
    transaction.setOutlet(venue);
    transaction.setWholesalePrice(5.0);
    transaction.setRetailPrice(10.0);
    return transaction;
  }
}
