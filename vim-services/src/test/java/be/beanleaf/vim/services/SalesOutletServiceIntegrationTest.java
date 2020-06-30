package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.fixtures.SalesOutletFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

class SalesOutletServiceIntegrationTest extends ServiceIntegrationTest {

  @Autowired
  private SalesOutletService salesOutletService;

  @Test
  void getDefaultSort() {
    assertThat(salesOutletService.getDefaultSort()).isEqualTo(Sort.by("description"));
  }

  @Test
  void findAll() {
    store(SalesOutletFixture.newSalesOutlet("outlet 1"));
    store(SalesOutletFixture.newSalesOutlet("outlet 2"));
    assertThat(salesOutletService.findAll(false)).hasSize(2);
    assertThat(salesOutletService.findAll(true)).isEmpty();
  }
}