package facades;
import dtos.OwnerDto;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserFacadeTest {
    private static EntityManagerFactory emf;
    private static UserFacade facade;

    private Owner o1, o2, o3;

    public UserFacadeTest() {
    }
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();

            o1= new Owner("Hans", "Jagtvej 60", "11223344");
            o2= new Owner("Bob", "Jagtvej 60", "11223344");
            o3= new Owner("Jens", "Jagtvej 60", "11223344");

            em.persist(o1);
            em.persist(o2);
            em.persist(o3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetAllOwners(){
        List<OwnerDto> actual = facade.getAllOwners();
        assert(actual.contains(new OwnerDto(o1)));
        assert(actual.contains(new OwnerDto(o2)));
        assert(actual.contains(new OwnerDto(o3)));
    }
}