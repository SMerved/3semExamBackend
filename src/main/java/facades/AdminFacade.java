package facades;
import dtos.BoatDto;
import entities.Boat;
import entities.Harbour;
import entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;


public class AdminFacade {

    private static AdminFacade instance;
    private static EntityManagerFactory emf;

    private AdminFacade() {}

    public static AdminFacade getAdminFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AdminFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public BoatDto createBoat(BoatDto boatDto) {
        EntityManager em = emf.createEntityManager();
        System.out.println(boatDto);
        Boat boat = new Boat(boatDto);

        try {
            em.getTransaction().begin();
            Harbour harbour = em.find(Harbour.class, boat.getHarbour().getId());
            boat.setHarbour(harbour);
            harbour.getBoats().add(boat);
            for (Owner ow : boat.getOwners()){
                if (ow.getId()!=null){
                    ow = em.find(Owner.class, ow.getId());
                }
                    ow.getBoats().add(boat);
            }
            em.persist(boat);
            for (Owner owner : boat.getOwners()){
                em.persist(owner);
            }
            em.persist(boat.getHarbour());
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new BoatDto(boat);
    }

}

