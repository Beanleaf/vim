package be.beanleaf.vim.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ProfileDto {

  @NotEmpty(message = "{validations.notEmpty.message}")
  private String name;
  @NotEmpty(message = "{validations.notEmpty.message}")
  @Email(message = "{validations.email.valid}")
  private String email;
  private String languageTag;

  public ProfileDto(String name, String email, String languageTag) {
    setName(name);
    setEmail(email);
    setLanguageTag(languageTag);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLanguageTag() {
    return languageTag;
  }

  public void setLanguageTag(String languageTag) {
    this.languageTag = languageTag;
  }
}
