package be.beanleaf.vim.domain.converters;

import be.beanleaf.vim.domain.PaymentType;
import javax.persistence.Converter;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class PaymentTypeConverter extends IntegerEnumConverter<PaymentType> {

}
