package be.beanleaf.vim.domain.converters;

import be.beanleaf.vim.domain.InventoryDirection;
import javax.persistence.Converter;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class InventoryDirectionConverter extends IntegerEnumConverter<InventoryDirection> {

}
