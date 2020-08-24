package be.beanleaf.vim.app.dto;

import javax.validation.constraints.NotEmpty;

public class VenueDto {

  @NotEmpty(message = "{validations.notEmpty.message}")
  private String name;
  private String description;

  public VenueDto() {

  }

  public VenueDto(
      String name,
      String description) {
    setName(name);
    setDescription(description);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
