package be.beanleaf.vim.domain.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sales_transactions")
public class SalesTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(optional = false)
  private SalesOutlet outlet;
  @Column(nullable = false)
  private Date timestamp;
  @Column(nullable = false)
  private Double wholesalePrice;
  @Column(nullable = false)
  private Double retailPrice;

  public SalesTransaction() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SalesOutlet getOutlet() {
    return outlet;
  }

  public void setOutlet(SalesOutlet outlet) {
    this.outlet = outlet;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Double getWholesalePrice() {
    return wholesalePrice;
  }

  public void setWholesalePrice(Double wholesalePrice) {
    this.wholesalePrice = wholesalePrice;
  }

  public Double getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(Double retailPrice) {
    this.retailPrice = retailPrice;
  }
}
