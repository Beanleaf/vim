package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController extends VimController {

  public static final String URL_LOGIN = "/login";
  public static final String URL_LOGIN_ERROR = "/login_error";
  public static final String URL_LOGOUT = "/logout";
  private static final String VIEW_LOGIN = "login";

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

  @GetMapping(URL_LOGOUT)
  public String logout() {
    getVimSession().setActiveUser(null);
    return redirect(HomeController.URL_HOME);
  }
}
