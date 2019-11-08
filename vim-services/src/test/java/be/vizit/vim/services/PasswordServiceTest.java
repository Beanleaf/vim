package be.vizit.vim.services;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTest extends ServiceIntegrationTest {
    @Autowired
    private PasswordService passwordService;

    @Test
    void createSaltHashPair() {
        String password = "thereIsNoSpoon";
        Pair<byte[], byte[]> saltHashPair = passwordService.createHashSaltPair(password);
        assertNotNull(saltHashPair.getLeft());
        assertNotNull(saltHashPair.getRight());
    }

    @Test
    void isExpectedPassword() {
        String password = "thereIsNoSpoon";
        Pair<byte[], byte[]> saltHashPair = passwordService.createHashSaltPair(password);
        byte[] salt = saltHashPair.getLeft();
        byte[] hash = saltHashPair.getRight();
        assertTrue(passwordService.isExpectedPassword(password, salt, hash));
        assertFalse(passwordService.isExpectedPassword("itIsAFork", salt, hash));
    }

    @Test
    void generateRandomPassword() {
        String password = passwordService.generateRandomPassword(10);
        assertThat(password.length()).isEqualTo(10);
        assertThat(password).doesNotContainAnyWhitespaces();
    }
}
