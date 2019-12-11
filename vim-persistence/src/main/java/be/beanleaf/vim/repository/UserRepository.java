package be.beanleaf.vim.repository;

import be.beanleaf.vim.domain.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>,
    JpaSpecificationExecutor<User> {

  User findUserById(Long id);

  User findUserByUsername(String username);

  User findUserByUuid(String uuid);

  User findUserByEmailAddress(String emailAddress);

}
