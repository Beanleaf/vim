package be.beanleaf.vim.app.controllers;

import be.beanleaf.vim.domain.entities.User;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @RequestMapping("/api/userTest")
  public User getUser() {
    User user1 = new User();
    user1.setActive(true);
    user1.setName("bob");
    return user1;
  }
}
