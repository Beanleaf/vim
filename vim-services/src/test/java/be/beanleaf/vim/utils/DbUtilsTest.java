package be.beanleaf.vim.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

class DbUtilsTest {

  @Test
  void combineAnd() {
    Specification<Object> spec1 = Specification
        .where((r, q, b) -> b.greaterThanOrEqualTo(r.get("hashCode"), 100));
    Specification<Object> spec2 = Specification
        .where((r, q, b) -> b.lessThanOrEqualTo(r.get("hashCode"), 100));
    List<Specification<Object>> specs = Arrays.asList(spec1, spec2);
    Specification<Object> result = DbUtils.combineAnd(specs);
    assertThat(result).isNotNull();
  }
}