package be.vizit.vim.app.controller;

import be.vizit.vim.app.VimSession;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    return VIEW_HOME;
  }
}
