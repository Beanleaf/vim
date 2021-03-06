package be.beanleaf.vim.domain.entities;

import be.beanleaf.vim.domain.UserRole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@Entity
@Table(name = "users")
public class User extends AbstractVimEntity {

  public static final Sort DEFAULT_SORT = Sort.by(Order.asc("name"));
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String username;
  private boolean active;
  private byte[] passwordSalt;
  private byte[] passwordHash;
  @Column(unique = true, nullable = false)
  private String emailAddress;
  private String phonenumber;
  private String name;
  private UserRole userRole;
  @Column(unique = true, nullable = false)
  private String uuid;
  private String languageTag;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public byte[] getPasswordSalt() {
    return passwordSalt;
  }

  public void setPasswordSalt(byte[] passwordSalt) {
    this.passwordSalt = passwordSalt;
  }

  public byte[] getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(byte[] passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getLanguageTag() {
    return languageTag;
  }

  public void setLanguageTag(String languageTag) {
    this.languageTag = languageTag;
  }
}
