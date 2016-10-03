package cz.vse.dao.impl;

import cz.vse.dao.PersonDao;
import cz.vse.entity.Person;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by pcejka on 03.10.2016.
 */
@Repository
public class PersonDaoImpl implements PersonDao {
    private final Logger l = Logger.getLogger(this.getClass());

    @PersistenceContext
    EntityManager em;

    @Override
    public void savePerson(Person person) {
        l.debug("Saving person: " + person);
        em.persist(person);
        l.info("Person saved successfully. Person detail: " + person);
    }

    @Override
    public void deletePerson(Person person) {
        l.debug("Deleting person: " + person);
        em.remove(person);
        l.info("Person deleted successfully. Person detail: " + person);
    }

    @Override
    public void updatePerson(Person person) {
        l.debug("Updating person:" + person);
        em.merge(person);
        l.info("Person updated successfully. Person detail: " + person);
    }

    @Override
    public List<Person> getAllPersons() {
        l.debug("Getting all person");

        List<Person> resultList = em.createQuery("select p from Person p").getResultList();
        l.info("Gotten all persons successfully. Person detail: " + resultList.toString());
        return resultList;

    }

    @Override
    public Person getPersonById(long id) {
        l.debug("Getting person by ID: " + id);
        Person person = em.find(Person.class, id);
        l.info("Gotten person successfully. Person detail: " + person);
        return person;

    }
}
