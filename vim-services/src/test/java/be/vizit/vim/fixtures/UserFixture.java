package be.vizit.vim.fixtures;

import be.vizit.vim.domain.entities.User;

public class UserFixture {

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
