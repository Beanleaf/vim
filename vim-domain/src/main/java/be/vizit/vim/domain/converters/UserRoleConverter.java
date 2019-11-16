package be.vizit.vim.domain.converters;

import be.vizit.vim.domain.UserRole;
import javax.persistence.Converter;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class UserRoleConverter extends IntegerEnumConverter<UserRole> {

}
