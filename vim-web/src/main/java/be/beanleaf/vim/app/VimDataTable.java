package be.beanleaf.vim.app;

import be.beanleaf.datatable.DataTable;
import be.beanleaf.datatable.DataTableColumn;
import java.util.List;

public abstract class VimDataTable<T> extends DataTable<T> {

  public VimDataTable(Integer page, int itemsPerPage) {
    super(page, itemsPerPage);
  }

  public VimDataTable(Integer page, int itemsPerPage, String navPosition) {
    super(page, itemsPerPage, navPosition);
  }

  @Override
  public List<DataTableColumn<T>> getColumns() {
    return null;
  }
}
