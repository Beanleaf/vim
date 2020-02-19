package be.beanleaf.vim.services;

import org.springframework.data.domain.Sort;

public interface VimService {

  Sort getDefaultSort();
}
