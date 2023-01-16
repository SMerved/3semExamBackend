package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.Set;

public class SetupTestUsers {


  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test");
    User admin = new User("admin", "test");
    User both = new User("user_admin", "test");

    Owner o1, o2, o3;
    Boat b1, b2, b3, b4, b5, b6;
    Harbour h1, h2;

    Set<Owner> owners1 = new HashSet<>();
    Set<Owner> owners2 = new HashSet<>();
    Set<Owner> owners3 = new HashSet<>();
    Set<Owner> owners4 = new HashSet<>();
    Set<Owner> owners5 = new HashSet<>();
    Set<Owner> owners6 = new HashSet<>();


    o1= new Owner("Hans", "Jagtvej 60", "11223344");
    o2= new Owner("Bob", "Jagtvej 60", "11223344");
    o3= new Owner("Jens", "Jagtvej 60", "11223344");

    h1= new Harbour("Harbour1", "Ved vandet", 10);
    h2= new Harbour("Harbour2", "Også ved vandet", 8);

    b1= new Boat("brand1", "fast", "boat1", "https://images.pexels.com/photos/163236/luxury-yacht-boat-speed-water-163236.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", h1, owners1);
    b2= new Boat("brand2", "faster", "boat2", "https://images.pexels.com/photos/163236/luxury-yacht-boat-speed-water-163236.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", h1, owners2);
    b3= new Boat("brand3", "fastest", "boat3", "https://images.pexels.com/photos/163236/luxury-yacht-boat-speed-water-163236.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", h2, owners3);
    b4= new Boat("brand4", "fast", "boat4", "https://images.pexels.com/photos/163236/luxury-yacht-boat-speed-water-163236.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", h1, owners4);
    b5= new Boat("brand5", "faster", "boat5", "https://images.pexels.com/photos/163236/luxury-yacht-boat-speed-water-163236.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", h1, owners5);
    b6= new Boat("brand6", "fastest", "boat6", "https://images.pexels.com/photos/163236/luxury-yacht-boat-speed-water-163236.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", h2, owners6);

    o1.getBoats().add(b1);
    o2.getBoats().add(b2);
    o3.getBoats().add(b3);
    o1.getBoats().add(b4);
    o2.getBoats().add(b5);
    o3.getBoats().add(b6);
    b1.getOwners().add(o1);
    b2.getOwners().add(o2);
    b3.getOwners().add(o3);
    b4.getOwners().add(o1);
    b5.getOwners().add(o2);
    b6.getOwners().add(o3);
    h1.getBoats().add(b1);
    h1.getBoats().add(b2);
    h2.getBoats().add(b3);
    h1.getBoats().add(b4);
    h1.getBoats().add(b5);
    h2.getBoats().add(b6);

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.persist(b1);
    em.persist(b2);
    em.persist(b3);
    em.persist(b4);
    em.persist(b5);
    em.persist(b6);
    em.persist(o1);
    em.persist(o2);
    em.persist(o3);
    em.persist(h1);
    em.persist(h2);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
