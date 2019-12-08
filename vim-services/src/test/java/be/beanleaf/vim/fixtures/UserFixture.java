package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.entities.User;

public abstract class UserFixture {

  public static User newUser(String username, String uuid, boolean isActive) {
    User user = new User();
    user.setUsername(username);
    user.setUuid(uuid);
    user.setActive(isActive);
    return user;
  }

  public static User newUser(String username, String uuid) {
    return newUser(username, uuid, false);
  }
}
