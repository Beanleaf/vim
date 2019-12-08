package be.beanleaf.vim.domain.converters;

import be.beanleaf.vim.domain.IntegerEnum;

@SuppressWarnings("unused")
abstract class IntegerEnumConverter<E extends IntegerEnum> extends
    AbstractEnumConverter<E, Integer> {

  @Override
  Integer getValue(E e) {
    return e.getId();
  }
}
