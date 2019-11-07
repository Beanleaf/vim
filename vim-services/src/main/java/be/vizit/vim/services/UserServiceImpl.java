package be.vizit.vim.services;

import be.vizit.vim.domain.User;
import be.vizit.vim.repository.UserRepository;
import be.vizit.vim.services.interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }
}
