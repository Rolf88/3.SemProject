package entity;

import infrastructure.IFlightRepository;
import java.util.List;
import javax.persistence.EntityManager;

public class FlightRepository implements IFlightRepository {

    private final EntityManager entityManager;

    public FlightRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<FlightEntity> findAllFlights() {
        return entityManager.createQuery("SELECT f FROM Flight f").getResultList();
    }

}
