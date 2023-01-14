package facades;
import dtos.BoatDto;
import dtos.OwnerDto;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserFacadeTest {
    private static EntityManagerFactory emf;
    private static UserFacade facade;

    private Owner o1, o2, o3;
    private Boat b1, b2, b3;
    private Harbour h1, h2;

    public UserFacadeTest() {
    }
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Harbour.deleteAllRows").executeUpdate();

            Set<Owner> owners1 = new HashSet<>();
            Set<Owner> owners2 = new HashSet<>();
            Set<Owner> owners3 = new HashSet<>();


            o1= new Owner("Hans", "Jagtvej 60", "11223344");
            o2= new Owner("Bob", "Jagtvej 60", "11223344");
            o3= new Owner("Jens", "Jagtvej 60", "11223344");

            h1= new Harbour("Harbour1", "Ved vandet", 10);
            h2= new Harbour("Harbour2", "Også ved vandet", 8);

            b1= new Boat("boat1", "fast", "water", "sailing", h1, owners1);
            b2= new Boat("boat2", "faster", "water", "sailing", h1, owners2);
            b3= new Boat("boat3", "fastest", "water", "sailing", h2, owners3);

            o1.getBoats().add(b1);
            o2.getBoats().add(b2);
            o3.getBoats().add(b3);
            b1.getOwners().add(o1);
            b2.getOwners().add(o2);
            b3.getOwners().add(o3);
            h1.getBoats().add(b1);
            h1.getBoats().add(b2);
            h2.getBoats().add(b3);


            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
            em.persist(o1);
            em.persist(o2);
            em.persist(o3);
            em.persist(h1);
            em.persist(h2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetAllOwners(){
        List<OwnerDto> actual = facade.getAllOwners();
        assert(actual.contains(new OwnerDto(o1)));
        assert(actual.contains(new OwnerDto(o2)));
        assert(actual.contains(new OwnerDto(o3)));
    }

    @Test
    public void testGetBoatsFromHarbour() throws Exception {
        List<BoatDto> actual = facade.getBoatsFromHarbour(h1.getId());
        assert (actual.contains(new BoatDto(b1)));
        assert (actual.contains(new BoatDto(b2)));
    }
}