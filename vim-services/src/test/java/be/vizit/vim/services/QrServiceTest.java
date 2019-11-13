package be.vizit.vim.services;

import static org.assertj.core.api.Assertions.assertThat;

import be.vizit.vim.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QrServiceTest extends AbstractTest {

  @Autowired
  private QrService qrService;

  @Test
  void generateQr() {
    assertThat(qrService.generateQr("input", 50, 50)).isNotNull();
    try {
      qrService.generateQr("", 50, 50);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IllegalArgumentException.class);
      assertThat(e.getMessage()).isEqualTo("The input while generating the QR code was not set.");
    }
  }
}
