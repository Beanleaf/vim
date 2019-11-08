package be.vizit.vim.services;

import be.vizit.vim.domain.User;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void validateUser() {
        assertTrue(userService.validateUser(new User()));
    }

    @Test
    void deactivateUser() {
        User user = new User();
        user.setActive(true);
        store(user);
        assertTrue(user.isActive());
        userService.deactivateUser(user.getId());
        assertFalse(user.isActive());
    }

    @Test
    void changePassword() {
        User user = new User();
        byte[] byte16 = new byte[16];
        user.setPasswordSalt(byte16);
        byte[] byte8 = new byte[8];
        user.setPasswordHash(byte8);
        store(user);
        userService.changePassword(user.getId(), Pair.of(new byte[1], new byte[2]));
        assertThat(user.getPasswordHash()).isEqualTo(new byte[1]);
        assertThat(user.getPasswordSalt()).isEqualTo(new byte[2]);
    }
}
