package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.UserRole;
import be.beanleaf.vim.domain.entities.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  User findUserById(Long id);

  User findUserByUsername(String username);

  List<User> findAllByUserRole(UserRole userRole);

  User findUserByUuid(String uuid);

  User findUserByEmailAddress(String emailAddress);

  long countAllByActive(boolean active);

  List<User> findAllByActive(boolean active, Pageable pageable);
}
