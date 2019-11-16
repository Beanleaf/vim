package be.vizit.vim.domain.converters;

import be.vizit.vim.domain.IntegerEnum;

@SuppressWarnings("unused")
public abstract class IntegerEnumConverter<E extends IntegerEnum> extends
    AbstractEnumConverter<E, Integer> {

  @Override
  Integer getValue(E e) {
    return e.getId();
  }
}
