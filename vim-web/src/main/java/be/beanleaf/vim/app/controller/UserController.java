package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends VimController {

  private static final String URL_NEW_USER = "/admin/users/new"; //FF maar
  private static final String VIEW_NEW_USER = "admin/users/newUser";

  private final UserService userService;

  @Autowired
  public UserController(VimSession vimSession, UserService userService) {
    super(vimSession);
    this.userService = userService;
  }

  @GetMapping(URL_NEW_USER)
  public String newUser(Model model) {
    return VIEW_NEW_USER;
  }

  @GetMapping(value = "admin/user/usernameCheck")
  public @ResponseBody
  boolean ajaxCheckUsernameAvailability(@RequestParam("toCheck") String username) {
    User userByUsername = userService.getUserByUsername(username);
    return userByUsername == null;
  }

  @GetMapping(value = "admin/user/emailCheck")
  public @ResponseBody
  boolean ajaxCheckEmailAvailability(@RequestParam("toCheck") String email) {
    User userByEmailAddress = userService.findUserByEmailAddress(email);
    return userByEmailAddress == null;
  }

}
