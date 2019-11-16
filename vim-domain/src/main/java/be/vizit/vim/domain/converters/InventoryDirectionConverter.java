package be.vizit.vim.domain.converters;

import be.vizit.vim.domain.InventoryDirection;
import javax.persistence.Converter;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class InventoryDirectionConverter extends IntegerEnumConverter<InventoryDirection> {

}
