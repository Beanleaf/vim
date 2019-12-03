package be.vizit.vim.app.utils.datatable;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class VimDataTable<T> {

  private static final String pageRequestParameter = "page";

  public static final String NAV_POS_TOP = "top";
  public static final String NAV_POS_BOT = "bot";
  public static final String NAV_POS_BOTH = "both";

  private final Pageable pageRequest;
  private final String url;
  private final String navPosition;

  public VimDataTable(Integer page, int itemsPerPage, String url) {
    this(page, itemsPerPage, url, NAV_POS_BOT);
  }

  public VimDataTable(Integer page, int itemsPerPage, String url, String navPosition) {
    this.pageRequest = PageRequest.of(page != null ? page - 1 : 0, itemsPerPage);
    this.url = url;
    this.navPosition = navPosition;
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

  public String getNavPosition() {
    return navPosition;
  }

  public abstract long getCount();

  public abstract List<T> getData();

  public abstract List<VimDataTableColumn<T>> getColumns();

  public List<String> getColumnTitles() {
    List<String> titles = new ArrayList<>();
    for (VimDataTableColumn<T> column : getColumns()) {
      titles.add(column.getTitle());
    }
    return titles;
  }
}
