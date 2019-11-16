package be.vizit.vim.domain.converters;

import java.lang.reflect.ParameterizedType;
import javax.persistence.AttributeConverter;

abstract class AbstractEnumConverter<ENUM, VALUE> implements AttributeConverter<ENUM, VALUE> {

  private final Class<ENUM> enumClass;

  @SuppressWarnings("unchecked")
  AbstractEnumConverter() {
    this.enumClass = (Class<ENUM>) ((ParameterizedType) this.getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
  }

  @Override
  public VALUE convertToDatabaseColumn(ENUM anEnum) {
    return anEnum != null ? getValue(anEnum) : null;
  }

  @Override
  public ENUM convertToEntityAttribute(VALUE value) {
    if (value == null) {
      return null;
    }

    return getEnumEntity(value);
  }

  abstract VALUE getValue(ENUM e);

  private ENUM getEnumEntity(VALUE value) {
    ENUM[] values = enumClass.getEnumConstants();
    if (value == null) {
      return null;
    }

    for (ENUM anEnum : values) {
      if (value.equals(getValue(anEnum))) {
        return anEnum;
      }
    }

    return null;
  }
}
