package be.vizit.vim.domain.converters;

import be.vizit.vim.domain.ItemStatus;
import javax.persistence.Converter;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class ItemStatusConverter extends IntegerEnumConverter<ItemStatus> {

}
