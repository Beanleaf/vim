package be.vizit.vim.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = "test")
public abstract class ServiceIntegrationTest {

  @PersistenceContext
  private EntityManager entityManager;

  public <T> T create(T t) {
    entityManager.persist(t);
    return t;
  }

  public <T> void store(T t) {
    entityManager.persist(t);
  }
}


