package be.beanleaf.vim.app.dto;

import be.beanleaf.vim.domain.UserRole;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserDto {

  @NotEmpty(message = "{validations.notEmpty.message}")
  private String name;
  @NotEmpty(message = "{validations.notEmpty.message}")
  @Length(max = 10, message = "{validations.length.10}")
  private String username;
  @NotEmpty(message = "{validations.notEmpty.message}")
  @Email(message = "{validations.email.valid}")
  private String email;
  @Length(max = 25, message = "{validations.length.25}")
  private String phonenumber;
  private boolean active;
  private boolean notifyMail;
  @NotNull
  private UserRole userRole;
  private String languageTag;

  public UserDto() {
    this(null, null, null, null, UserRole.STANDARD, true, true, null);
  }

  public UserDto(String name, String username, String email,
      String phonenumber, UserRole userRole, boolean active, boolean notifyMail,
      String languageTag) {
    setName(name);
    setUsername(username);
    setEmail(email);
    setPhonenumber(phonenumber);
    setUserRole(userRole);
    setActive(active);
    setNotifyMail(notifyMail);
    setLanguageTag(languageTag);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isNotifyMail() {
    return notifyMail;
  }

  public void setNotifyMail(boolean notifyMail) {
    this.notifyMail = notifyMail;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  public String getLanguageTag() {
    return languageTag;
  }

  public void setLanguageTag(String languageTag) {
    this.languageTag = languageTag;
  }
}
