package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends VimController {

  @Autowired
  public HomeController(VimSession vimSession) {
    super(vimSession);
  }

  @GetMapping(value = {"/", "/home"})
  public String home() {
    return "home";
  }
}
