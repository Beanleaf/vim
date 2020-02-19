package be.beanleaf.vim.services;

import org.springframework.data.domain.Sort;

public abstract class AbstractVimService implements VimService {

  @Override
  public Sort getDefaultSort() {
    return Sort.by("id");
  }
}
