package be.vizit.vim.repository;

import be.vizit.vim.domain.UserRole;
import be.vizit.vim.domain.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findUserById(Long id);

  User findUserByUsername(String username);

  List<User> findAllByUserRole(UserRole userRole);

  User findUserByUuid(String uuid);
}
