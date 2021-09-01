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

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);

        em.getTransaction().commit();
        
        System.out.println("p1: " + p1.getName() + " " + p1.getId());
        System.out.println("p2: " + p2.getName() + " " + p2.getId());
    }

}
