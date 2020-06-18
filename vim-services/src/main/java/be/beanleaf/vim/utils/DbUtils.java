package be.beanleaf.vim.utils;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

public final class DbUtils {

  private DbUtils() {
  }

  public static <T> Specification<T> combineAnd(List<Specification<T>> input) {
    Specification<T> result = Specification.where(null);
    if (CollectionUtils.isEmpty(input)) {
      return result;
    }
    for (Specification<T> spec : input) {
      if (result != null && spec != null) {
        result = result.and(spec);
      }
    }
    return result;
  }
}
