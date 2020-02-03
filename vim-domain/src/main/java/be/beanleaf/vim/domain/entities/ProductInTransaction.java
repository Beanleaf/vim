package be.beanleaf.vim.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products_in_transaction")
public class ProductInTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(optional = false)
  private Product product;
  @ManyToOne(optional = false)
  private SalesTransaction salesTransaction;
  @Column(nullable = false)
  private Long amount;

  public ProductInTransaction() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public SalesTransaction getSalesTransaction() {
    return salesTransaction;
  }

  public void setSalesTransaction(SalesTransaction salesTransaction) {
    this.salesTransaction = salesTransaction;
  }

  public Long getAmount() {
    return amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }
}
