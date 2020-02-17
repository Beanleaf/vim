package be.beanleaf.vim.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private Double wholesalePrice;
  @Column(nullable = false)
  private String name;
  private String supplier;
  private boolean inCrates;
  @ManyToOne(optional = false)
  private ProductCategory productCategory;
  @ManyToOne(optional = false)
  private SalesOutlet salesOutlet;
  private boolean active;

  public Product() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getWholesalePrice() {
    return wholesalePrice;
  }

  public void setWholesalePrice(Double wholesalePrice) {
    this.wholesalePrice = wholesalePrice;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public boolean isInCrates() {
    return inCrates;
  }

  public void setInCrates(boolean inCrates) {
    this.inCrates = inCrates;
  }

  public ProductCategory getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ProductCategory productCategory) {
    this.productCategory = productCategory;
  }

  public SalesOutlet getSalesOutlet() {
    return salesOutlet;
  }

  public void setSalesOutlet(SalesOutlet salesOutlet) {
    this.salesOutlet = salesOutlet;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
