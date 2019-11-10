package be.vizit.vim.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vizit.vim.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QrServiceTest extends AbstractTest {

  @Autowired
  private QrService qrService;

  @Test
  void generateQr() {
    assertNotNull(qrService.generateQr("input", 50, 50));
    assertThrows(IllegalArgumentException.class, () -> qrService.generateQr("", 50, 50));
  }
}
