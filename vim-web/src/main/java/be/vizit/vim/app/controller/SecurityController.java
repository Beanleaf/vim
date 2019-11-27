package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController extends VimController {


  @Autowired
  SecurityController(VimSession vimSession) {
    super(vimSession);
  }

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/login_error")
  public String loginErrror(Model model) {
    model.addAttribute("error", true);
    return "login";
  }

  @GetMapping("/logout")
  public String logout() {
    getVimSession().setActiveUser(null);
    return "home";
  }
}
