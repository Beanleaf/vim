package be.vizit.vim.app.controller;

import be.vizit.vim.app.ToastMessage;
import be.vizit.vim.app.VimSession;
import be.vizit.vim.domain.utilities.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends VimController {

  public static final String URL_HOME = "/";
  private static final String VIEW_HOME = "public/home";

  @Autowired
  public HomeController(VimSession vimSession) {
    super(vimSession);
  }

  @GetMapping(value = {URL_HOME, "/home"})
  public String home() {
    return VIEW_HOME;
  }

  @GetMapping(value = URL_HOME, params = {"logout"})
  public String succesfullLogout(Model model) {
    model.addAttribute(new ToastMessage(MessageType.INFO, "notifications.loggedout"));
    return VIEW_HOME;
  }
}
