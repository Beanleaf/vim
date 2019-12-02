package be.vizit.vim.app.utils;

import be.vizit.vim.app.dto.ColumnDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class VimDataTable<T> {

  private static final String pageRequestParameter = "page";
  private final Pageable pageRequest;
  private final String url;

  public VimDataTable(Integer page, int itemsPerPage, String url) {
    this.pageRequest = PageRequest.of(page != null ? page - 1 : 0, itemsPerPage);
    this.url = url;
  }

  public Pageable getPageRequest() {
    return pageRequest;
  }

  public String getPageUrl(int pageNumber) {
    return url + "?" + pageRequestParameter + "=" + pageNumber;
  }

  public int getCurrentPage() {
    return pageRequest.getPageNumber();
  }

  public int getPageSize() {
    return pageRequest.getPageSize();
  }

  public int getTotalPages() {
    return (int) Math.ceil((double) getCount() / getPageSize());
  }

  public abstract int getCount();

  public abstract List<T> getData();

  protected abstract List<VimDataTableColumn<T>> getColumns();

  public List<String> getColumnTitles() {
    List<String> titles = new ArrayList<>();
    for (VimDataTableColumn<T> column : getColumns()) {
      titles.add(column.getTitle());
    }
    return titles;
  }

  public List<ColumnDto> getColumnData(T object) {
    List<ColumnDto> columnData = new ArrayList<>();
    for (VimDataTableColumn<T> column : getColumns()) {
      columnData.add(new ColumnDto(column.getText(object), column.isRawHtml()));
    }
    return columnData;
  }
}
