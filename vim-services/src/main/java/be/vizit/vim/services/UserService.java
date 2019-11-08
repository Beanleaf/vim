package be.vizit.vim.services;

import be.vizit.vim.domain.UserRole;
import be.vizit.vim.domain.entities.User;
import be.vizit.vim.repository.UserRepository;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public User getUser(long id) {
    return userRepository.findUserById(id);
  }

  @Transactional(readOnly = true)
  public User getUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }

  @Transactional(readOnly = true)
  public List<User> findUsersByRole(UserRole role) {
    return userRepository.findAllByUserRole(role);
  }

  @Transactional
  public void deactivateUser(long id) {
    User user = getUser(id);
    user.setActive(false);
  }

  @Transactional
  public void changePassword(long userId, Pair<byte[], byte[]> hashSalt) {
    User user = getUser(userId);
    user.setPasswordHash(hashSalt.getLeft());
    user.setPasswordSalt(hashSalt.getRight());
  }
}
