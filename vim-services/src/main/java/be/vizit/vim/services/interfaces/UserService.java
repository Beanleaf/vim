package be.vizit.vim.services.interfaces;

import be.vizit.vim.domain.User;

public interface UserService {
    User getUser(long id);

    User getUser(String username);

    boolean validateUser(User user);
}
