package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDto;
import dtos.OwnerDto;
import entities.*;
import facades.UserFacade;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static UserFacade facade;

    private Owner o1, o2, o3;
    private Boat b1, b2, b3;
    private Harbour h1, h2;

    public UserResourceTest() {
    }

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

        facade = UserFacade.getUserFacade(emf);

    }


    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Harbour.deleteAllRows").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);

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

            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
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

    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/info").then().statusCode(200);
    }


    @Test
    public void testGetAllOwners() throws Exception {
        List<OwnerDto> ownerDtos;
        login("user", "test");
        ownerDtos = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/boats/owners")
                .then()
                .extract().body().jsonPath().getList("", OwnerDto.class);

        assertThat(ownerDtos, containsInAnyOrder(new OwnerDto(o1), new OwnerDto(o2), new OwnerDto(o3)));
    }

    @Test
    public void testGetBoatsFromHarbour() throws Exception {
        List<BoatDto> boatDtos;
        login("user", "test");
        boatDtos = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/boats/"+ h1.getId())
                .then()
                .extract().body().jsonPath().getList("", BoatDto.class);

        assertThat(boatDtos, containsInAnyOrder(new BoatDto(b1), new BoatDto(b2)));
    }

}
