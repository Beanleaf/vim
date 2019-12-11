package be.beanleaf.vim.services;

import be.beanleaf.vim.domain.UserRole;
import be.beanleaf.vim.domain.entities.InventoryLog;
import be.beanleaf.vim.domain.entities.User;
import be.beanleaf.vim.domain.utilities.ValidationException;
import be.beanleaf.vim.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final InventoryLogService inventoryLogService;

  @Autowired
  public UserService(UserRepository userRepository, InventoryLogService inventoryLogService) {
    this.userRepository = userRepository;
    this.inventoryLogService = inventoryLogService;
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
  public List<User> findActiveUsersByRole(UserRole role) {
    return userRepository.findAll(
        buildSpec(null, true, role),
        Pageable.unpaged()).getContent();
  }

  @Transactional(readOnly = true)
  public User findUserByEmailAddress(String emailAddress) {
    return userRepository.findUserByEmailAddress(emailAddress);
  }

  @Transactional(readOnly = true)
  public List<User> findUsers(String name, boolean active, UserRole userRole, Pageable page) {
    return userRepository.findAll(buildSpec(name, active, userRole), page)
        .getContent();
  }

  @Transactional(readOnly = true)
  public long countUsers(String name, boolean active, UserRole userRole) {
    return userRepository.count(buildSpec(name, active, userRole));
  }

  private Specification<User> buildSpec(String name, boolean active, UserRole userRole) {
    List<Specification<User>> specs = new ArrayList<>();
    if (!StringUtils.isEmpty(name)) {
      String searchString = "%" + name.toUpperCase() + "%";
      Specification<User> nameSpec = (user, cq, cb) -> cb
          .like(cb.upper(user.get("username")), searchString);
      nameSpec = nameSpec
          .or((user, cq, cb) -> cb.like(cb.upper(user.get("firstName")), searchString));
      nameSpec = nameSpec != null ? nameSpec
          .or((user, cq, cb) -> cb.like(cb.upper(user.get("lastName")), searchString)) : null;
      specs.add(nameSpec);
    }

    if (userRole != null) {
      specs.add((user, cq, cb) -> cb.equal(user.get("userRole"), userRole));
    }
    specs.add((user, cq, cb) -> cb.equal(user.get("active"), active));

    Specification<User> resultSpec = Specification.where(null);
    for (Specification<User> spec : specs) {
      if (resultSpec != null) {
        resultSpec = resultSpec.and(spec);
      }
    }
    return resultSpec;
  }

  @Transactional
  public void deactivateUser(User user) {
    user.setActive(false);
  }

  @Transactional
  public void changePassword(long userId, Pair<byte[], byte[]> hashSalt) {
    User user = getUser(userId);
    user.setPasswordHash(hashSalt.getLeft());
    user.setPasswordSalt(hashSalt.getRight());
  }

  public User createNewUser(boolean active, String emailAddress, String firstName, String lastName,
      String phonenumber, UserRole role, String username) {
    User user = new User();
    user.setActive(active);
    user.setEmailAddress(emailAddress);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPhonenumber(phonenumber);
    user.setUserRole(role);
    user.setUsername(username);
    user.setUuid(UUID.randomUUID().toString());
    return user;
  }

  @Transactional
  public void updateUser(User user, String email, boolean active, String firstName, String lastName,
      String phonenumber, UserRole userRole, String username) {
    user.setEmailAddress(email);
    user.setActive(active);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPhonenumber(phonenumber);
    user.setUserRole(userRole);
    user.setUsername(username);
  }

  @Transactional(readOnly = true)
  public User login(String uuidOrUsername) {
    User user = getUserByUuid(uuidOrUsername);
    if (user == null) {
      user = getUserByUsername(uuidOrUsername);
    }
    return user != null && user.isActive() ? user : null;
  }

  @Transactional(readOnly = true)
  public boolean isDeletable(User user) {
    List<InventoryLog> logs = inventoryLogService.findAllLogsForUser(user);
    return CollectionUtils.isEmpty(logs) && !user.isActive();
  }

  @Transactional
  public void save(User user) {
    userRepository.save(user);
  }

  @Transactional
  public void delete(User user) {
    if (isDeletable(user)) {
      userRepository.delete(user);
    } else {
      throw new ValidationException("error.user.notDeletable");
    }
  }
}
