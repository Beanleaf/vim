package be.vizit.vim.services;

import be.vizit.vim.domain.UserRole;
import be.vizit.vim.domain.entities.User;
import be.vizit.vim.domain.utilities.ValidationException;
import be.vizit.vim.repository.UserRepository;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.internet.InternetAddress;
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
  public User getUserByUuid(String uuid) {
    return userRepository.findUserByUuid(uuid);
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

  @Transactional(readOnly = true)
  public User login(String uuidOrUsername) {
    User user = getUserByUuid(uuidOrUsername);
    if (user == null) {
      user = getUserByUsername(uuidOrUsername);
    }
    return user != null && user.isActive() ? user : null;
  }

  public InternetAddress getInternetAddress(User user) {
    try {
      return new InternetAddress(user.getEmailAddress(), user.getShortName());
    } catch (UnsupportedEncodingException e) {
      throw new ValidationException(e.getMessage());
    }
  }
}
