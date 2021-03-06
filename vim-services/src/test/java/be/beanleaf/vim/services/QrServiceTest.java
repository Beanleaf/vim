package be.beanleaf.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QrServiceTest extends AbstractTest {

  @Autowired
  private QrService qrService;

  @Test
  @SuppressWarnings("CatchMayIgnoreException")
  void generateQr() {
    assertThat(qrService.generateQr("input", "png", 50, 50)).isNotNull();
    try {
      qrService.generateQr("", "png", 50, 50);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IllegalArgumentException.class);
      assertThat(e.getMessage()).isEqualTo("The input while generating the QR code was not set.");
    }
  }

  @Test
  void getQrPngSrc() {
    assertThat(qrService.getQrPngSrc("input", 10, 10)).isNotNull();
  }
}
