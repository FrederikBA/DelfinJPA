package facades;

import entities.Address;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class PersonFacade {

    private static EntityManagerFactory emf;
    private static PersonFacade instance;

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {

        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    public Person getPersonById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person customer = em.find(Person.class, id);
            return customer;
        } finally {
            em.close();
        }
    }

    public List<Person> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT customer FROM Customer customer", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Person createPerson(Person p) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }

    public Person updatePerson(Person p) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("UPDATE Person p WHERE p.id = :id", Person.class);
            return p;
        } finally {
            em.close();
        }
    }
    
}
