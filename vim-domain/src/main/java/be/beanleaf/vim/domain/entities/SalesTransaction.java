package be.beanleaf.vim.domain.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sales_transactions")
public class SalesTransaction extends AbstractVimEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(optional = false)
  private SalesOutlet outlet;
  @Column(nullable = false)
  private LocalDateTime timestamp;
  @Column(nullable = false)
  private Double wholesalePrice;
  @Column(nullable = false)
  private Double retailPrice;

  @Override
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

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
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
