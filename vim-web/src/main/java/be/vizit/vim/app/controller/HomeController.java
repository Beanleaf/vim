package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends VimController {

  public static final String URL_HOME = "/";
  private static final String VIEW_HOME = "home";

  @Autowired
  public HomeController(VimSession vimSession) {
    super(vimSession);
  }

  @GetMapping(value = {URL_HOME, "/home"})
  public String home() {
    //TODO: add succesfull logout message
    return VIEW_HOME;
  }
}
