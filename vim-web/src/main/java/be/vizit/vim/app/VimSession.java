package be.vizit.vim.app;

import be.vizit.vim.domain.entities.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VimSession {

  private User activeUser;

  public User getActiveUser() {
    return activeUser;
  }

  public void setActiveUser(User activeUser) {
    this.activeUser = activeUser;
  }
}
