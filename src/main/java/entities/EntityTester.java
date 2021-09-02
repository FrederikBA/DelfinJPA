package entities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class EntityTester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Jonke", 1963);
        Person p2 = new Person("Blondie", 1959);

        Address a1 = new Address("Store Torv 1", 2323, "Nr Snede");
        Address a2 = new Address("Langgade 34", 1212, "Valby");

        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(300);

        p1.addFee(f1);
        p1.addFee(f3);
        p2.addFee(f2);

        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("Butterfly");
        SwimStyle s3 = new SwimStyle("Breast Stroke");

        p1.addSwimStyle(s1);
        p1.addSwimStyle(s3);
        p2.addSwimStyle(s2);

        p1.setAddress(a1);
        p2.setAddress(a2);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        p1.removeSwimStyle(s3);
        em.getTransaction().commit();

        System.out.println("p1: " + p1.getName() + " " + p1.getId());
        System.out.println("p2: " + p2.getName() + " " + p2.getId());

        System.out.println("Jonkes gade: " + p1.getAddress().getStreet());
        System.out.println("Hvem bor på Store Torv 1: " + a1.getPerson().getName());

        System.out.println("Hvem har betalt f2? Det har: " + f2.getPerson().getName());

        System.out.println("Betalinger:");

        TypedQuery<Fee> query = em.createQuery("SELECT f FROM Fee f", Fee.class);

        List<Fee> fees = query.getResultList();

        for (Fee f : fees) {
            System.out.println(f.getPerson().getName() + " " + f.getAmount() + " " + f.getPayDate());
        }
    }

}
