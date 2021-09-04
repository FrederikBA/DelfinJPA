package entry;

import entities.Address;
import entities.Person;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class Main {
    
   
    
    public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        PersonFacade facade = PersonFacade.getPersonFacade(emf);
        Address a1 = new Address("Valbygade 3", 2800, "Valby");
        Person p3 = facade.createPerson(new Person("Janus", 1954));
        p3.setAddress(a1);
        System.out.println("This person has been created: " + p3);
    }

}
