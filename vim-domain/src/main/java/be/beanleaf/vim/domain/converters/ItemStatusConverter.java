package be.beanleaf.vim.domain.converters;

import be.beanleaf.vim.domain.ItemStatus;
import javax.persistence.Converter;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class ItemStatusConverter extends IntegerEnumConverter<ItemStatus> {

}
