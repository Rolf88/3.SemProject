package facades;

import infrastructure.IAdminService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import models.ReservationModel;

public class AdminFacade implements IAdminService{

    private final EntityManager entityManager;

    public AdminFacade(EntityManagerFactory factory) {
        this.entityManager = factory.createEntityManager();
    }
    
    @Override
    public List<ReservationModel> getReservations() {
        Query query = entityManager.createQuery("SELECT r FROM Reservation r");
        
        return query.getResultList();
    }

}
