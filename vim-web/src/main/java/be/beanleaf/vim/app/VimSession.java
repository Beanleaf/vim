package be.beanleaf.vim.app;

import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VimSession {

  private final UserService userService;

  private User activeUser;

  @Autowired
  public VimSession(UserService userService) {
    this.userService = userService;
  }

  public User getActiveUser() {
    return activeUser;
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }

  public void refreshUser() {
    this.activeUser = userService.getUser(activeUser.getId());
  }
}
