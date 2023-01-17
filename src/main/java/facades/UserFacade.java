package facades;

import dtos.BoatDto;
import dtos.OwnerDto;
import entities.Boat;
import entities.Owner;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }


    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public List<OwnerDto> getAllOwners(){
            EntityManager em = emf.createEntityManager();
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
            List<Owner> owners = query.getResultList();


            List<OwnerDto> ownerDtos = new ArrayList<>();
            for (Owner owner : owners) {
                ownerDtos.add(new OwnerDto(owner));
            }
        System.out.println(ownerDtos);
            return ownerDtos;
        }

    public List<BoatDto> getAllBoats(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
        List<Boat> boats = query.getResultList();


        List<BoatDto> boatDtos = new ArrayList<>();
        for (Boat boat : boats) {
            boatDtos.add(new BoatDto(boat));
        }

        return boatDtos;
    }

    public List<BoatDto> getBoatsFromHarbour(Long id) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Boat> query = (TypedQuery<Boat>) em.createNativeQuery("SELECT * FROM boats WHERE harbour_id = ? ", Boat.class);
        query.setParameter(1, id);
        List<Boat> boatList = query.getResultList();
        List<BoatDto> boatDtos = new ArrayList<>();
        for (Boat boat : boatList) {
            boatDtos.add(new BoatDto(boat));
        }
        return boatDtos;
    }

    public List<OwnerDto> getOwnersFromBoat(Long id) {
        EntityManager em = emf.createEntityManager();
        Boat boat;
        boat = em.find(Boat.class, id);
        Set<Owner> owners = boat.getOwners();

        List<OwnerDto> ownerDtos = new ArrayList<>();
        for (Owner owner : owners) {
            ownerDtos.add(new OwnerDto(owner));
        }
        return ownerDtos;
    }

}
