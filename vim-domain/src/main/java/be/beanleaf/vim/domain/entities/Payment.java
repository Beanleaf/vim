package be.beanleaf.vim.domain.entities;

import be.beanleaf.vim.domain.PaymentType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment extends AbstractVimEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(optional = false)
  private SalesTransaction salesTransaction;
  @Column(nullable = false)
  private Double amount;
  @Column(nullable = false)
  private PaymentType paymentType;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SalesTransaction getSalesTransaction() {
    return salesTransaction;
  }

  public void setSalesTransaction(SalesTransaction salesTransaction) {
    this.salesTransaction = salesTransaction;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }
}
