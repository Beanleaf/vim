package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import be.vizit.vim.services.QrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController extends VimController {

  private final QrService qrService;

  @Autowired
  public HomeController(VimSession vimSession, QrService qrService) {
    super(vimSession);
    this.qrService = qrService;
  }

  @GetMapping(value = {"/", "/home"})
  public String home() {
    return "home";
  }

  @GetMapping(value = "/qr", produces = MediaType.IMAGE_PNG_VALUE)
  public @ResponseBody
  byte[] getQr(@RequestParam(name = "content") String input) {
    return qrService.generateQr(StringUtils.trim(input), 200, 200);
  }
}