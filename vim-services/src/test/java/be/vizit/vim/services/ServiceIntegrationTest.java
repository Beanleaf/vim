package be.vizit.vim.services;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
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


