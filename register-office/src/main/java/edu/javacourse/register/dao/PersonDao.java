package edu.javacourse.register.dao;

import edu.javacourse.register.domain.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class PersonDao {
    private EntityManager entityManager;

    public PersonDao() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
        entityManager = factory.createEntityManager();
    }

    public List<Person> findPersons() {
        return entityManager.createQuery("SELECT p FROM Person p").getResultList();
    }
}
