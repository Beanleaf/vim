package be.vizit.vim.repository;

import be.vizit.vim.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class UserRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindUserById() {
        User testUser = new User();
        testUser.setUsername("bob");
        userRepository.save(testUser);
        assertThat(testUser.getId(), is(notNullValue()));
    }
}
