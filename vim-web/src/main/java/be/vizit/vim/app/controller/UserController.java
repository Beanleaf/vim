package be.vizit.vim.app.controller;

import be.vizit.vim.app.UserSession;
import be.vizit.vim.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

  private final UserService userService;
  private final UserSession userSession;

  @Autowired
  public UserController(UserService userService, UserSession userSession) {
    this.userService = userService;
    this.userSession = userSession;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/login")
  public String loginSubmit(@RequestParam("loginId") String uuid) {
    userSession.setUser(userService.findUserByUuid(uuid));
    return "login";
  }
}
