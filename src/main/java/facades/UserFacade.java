package facades;

import dtos.OwnerDto;
import entities.Owner;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
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
            TypedQuery<Owner> query = em.createQuery("SELECT r FROM Owner r", Owner.class);
            List<Owner> owners = query.getResultList();


            ArrayList<OwnerDto> ownerDtos = new ArrayList<>();
            for (Owner owner : owners) {
                ownerDtos.add(new OwnerDto(owner));
            }
        System.out.println(ownerDtos);
            return ownerDtos;
        }

}
