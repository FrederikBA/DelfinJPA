package entities;

import dto.PersonFeeDTO;
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
        Person p3 = new Person("Janus", 1996);

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
        p3.addSwimStyle(s1);

        p1.setAddress(a1);
        p2.setAddress(a2);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
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

        TypedQuery<Fee> query1 = em.createQuery("SELECT f FROM Fee f", Fee.class);

        List<Fee> fees = query1.getResultList();

        for (Fee f : fees) {
            System.out.println(f.getPerson().getName() + " " + f.getAmount() + " " + f.getPayDate());
        }
        TypedQuery<PersonFeeDTO> query2 = em.createQuery("SELECT new dto.PersonFeeDTO(p.name, f.amount) FROM Person p JOIN p.fees f", PersonFeeDTO.class);

        List<PersonFeeDTO> personsAndFees = query2.getResultList();

        for (PersonFeeDTO p : personsAndFees) {
            System.out.println(p.getName() + "," + p.getFee());
        }

        TypedQuery<Person> query3 = em.createQuery("SELECT person FROM Person person JOIN person.styles s WHERE s.styleName = 'Crawl'", Person.class);

        List<Person> crawlPersons = query3.getResultList();

        for (Person p : crawlPersons) {
            System.out.println(p.getName());
        }
        TypedQuery<Long> query4 = em.createQuery("SELECT SUM(fee.amount) FROM Fee fee", Long.class);

        Long feeSum = query4.getSingleResult();

        System.out.println(feeSum);

        TypedQuery<Integer> query5 = em.createQuery("SELECT MIN(fee.amount) FROM Fee fee", Integer.class);

        TypedQuery<Integer> query6 = em.createQuery("SELECT MAX(fee.amount) FROM Fee fee", Integer.class);

        Integer smallestFee = query5.getSingleResult();

        Integer highestFee = query6.getSingleResult();

        System.out.println("The smallest fee is: " + smallestFee + " and the highest fee is: " + highestFee);
    }
}
