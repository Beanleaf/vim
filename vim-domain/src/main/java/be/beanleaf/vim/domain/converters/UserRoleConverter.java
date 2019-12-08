package be.beanleaf.vim.domain.converters;

import be.beanleaf.vim.domain.UserRole;
import javax.persistence.Converter;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class UserRoleConverter extends IntegerEnumConverter<UserRole> {

}
