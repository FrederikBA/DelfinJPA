package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityTester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Jonke", 1963);
        Person p2 = new Person("Blondie", 1959);

        Address a1 = new Address("Store Torv 1", 2323, "Nr Snede");
        Address a2 = new Address("Langgade 34", 1212, "Valby");

        p1.setAddress(a1);
        p2.setAddress(a2);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();

        System.out.println("p1: " + p1.getName() + " " + p1.getId());
        System.out.println("p2: " + p2.getName() + " " + p2.getId());

        System.out.println("Jonkes gade: " + p1.getAddress().getStreet());
        System.out.println("Hvem bor på Store Torv 1: " + a1.getPerson().getName());
    }

}
