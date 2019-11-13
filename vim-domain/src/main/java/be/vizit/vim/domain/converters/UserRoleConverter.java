package be.vizit.vim.domain.converters;

import be.vizit.vim.domain.UserRole;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {

  @Override
  public Integer convertToDatabaseColumn(UserRole userRole) {
    return userRole != null ? userRole.getId() : null;
  }

  @Override
  public UserRole convertToEntityAttribute(Integer integer) {
    if (integer == null) {
      return null;
    }

    for (UserRole role : UserRole.values()) {
      if (role.getId() == integer) {
        return role;
      }
    }
    return null;
  }
}
