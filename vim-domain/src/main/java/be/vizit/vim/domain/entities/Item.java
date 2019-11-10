package be.vizit.vim.domain.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Date lastUpdate;
  @ManyToOne
  private User lastRenter;
  private boolean isActive;
  private String uuid;

  public Item() {
  }
}
