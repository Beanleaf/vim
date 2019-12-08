package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController extends VimController {

  public static final String URL_LOGIN = "/login";
  private static final String VIEW_LOGIN = "public/login";
  public static final String URL_LOGIN_ERROR = "/login_error";

  @Autowired
  SecurityController(VimSession vimSession) {
    super(vimSession);
  }

  @GetMapping(URL_LOGIN)
  public String loginPage() {
    return VIEW_LOGIN;
  }

  @GetMapping(URL_LOGIN_ERROR)
  public String loginErrror(Model model) {
    model.addAttribute("error", true);
    return VIEW_LOGIN;
  }
}
