package be.vizit.vim.services;

import be.vizit.vim.domain.User;
import be.vizit.vim.services.interfaces.UserService;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class UserServiceIntegrationTest extends ServiceIntegrationTest {

    private UserService userService = new UserServiceImpl(null);

    @Test
    void validateUser() {
        assertThat(userService.validateUser(new User()), equalTo(Boolean.TRUE));
    }
}
