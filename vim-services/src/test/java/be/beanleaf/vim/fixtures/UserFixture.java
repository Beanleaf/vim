package be.beanleaf.vim.fixtures;

import be.beanleaf.vim.domain.entities.User;

public abstract class UserFixture {

  public static User newUser(String username, String uuid, String email, boolean isActive) {
    User user = new User();
    user.setUsername(username);
    user.setUuid(uuid);
    user.setActive(isActive);
    user.setEmailAddress(email);
    return user;
  }

  public static User newUser(String username, String uuid, String email) {
    return newUser(username, uuid, email, false);
  }
}
