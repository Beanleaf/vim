package be.vizit.vim.app.controller;

import be.vizit.vim.services.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

  private final QrService qrService;

  @Autowired
  public HomeController(QrService qrService) {
    this.qrService = qrService;
  }

  @GetMapping("/home")
  public String home() {
    return "home";
  }

  @GetMapping(value = "/qr", produces = MediaType.IMAGE_PNG_VALUE)
  public @ResponseBody
  byte[] getQr() {
    return qrService.generateQr("Ik hou van jou", 50, 50);
  }
}
