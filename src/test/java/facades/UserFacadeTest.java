package facades;

import dtos.UserDto;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserFacadeTest {
    private static EntityManagerFactory emf;
    private static UserFacade facade;

    private User u1, u2, u3;

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
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();

            Role userRole = new Role("user");
            u1 = new User("user1", "test1", "Jagtvej 60", "11223344");
            u1.addRole(userRole);
            u2 = new User("user2", "test2", "Jagtvej 60", "11223344");
            u2.addRole(userRole);
            u3 = new User("user3", "test3", "Jagtvej 60", "11223344");
            u3.addRole(userRole);

            em.persist(userRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(u3);

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
    public void testGetAllExercises(){
        List<UserDto> actual = facade.getAllOwners;
        assert(actual.contains(new UserDto(u1)));
        assert(actual.contains(new UserDto(u2)));
        assert(actual.contains(new UserDto(u3)));
    }
}