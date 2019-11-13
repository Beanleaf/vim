package be.vizit.vim.services;

import be.vizit.vim.AbstractTest;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class ServiceIntegrationTest extends AbstractTest {

  @PersistenceContext
  private EntityManager entityManager;

  public <T> T create(T t) {
    entityManager.persist(t);
    return t;
  }

  public <T> void store(T t) {
    entityManager.persist(t);
  }

  @SafeVarargs
  public final <T> void storeAll(T... t) {
    for (T object : t) {
      store(object);
    }
  }
}


