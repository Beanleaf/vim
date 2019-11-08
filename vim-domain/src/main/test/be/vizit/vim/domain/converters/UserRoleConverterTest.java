package be.vizit.vim.domain.converters;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.domain.UserRole;
import org.junit.jupiter.api.Test;

class UserRoleConverterTest {

  @Test
  void convertToDatabaseColumn() {
    UserRoleConverter userRoleConverter = new UserRoleConverter();
    for (UserRole userRole : UserRole.values()) {
      assertThat(userRoleConverter.convertToDatabaseColumn(userRole)).isEqualTo(userRole.getId());
    }
  }

  @Test
  void convertToEntityAttribute() {
    UserRoleConverter userRoleConverter = new UserRoleConverter();
    for (UserRole userRole : UserRole.values()) {
      assertThat(userRoleConverter.convertToEntityAttribute(userRole.getId())).isEqualTo(userRole);
    }
  }
}