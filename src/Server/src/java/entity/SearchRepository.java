package entity;

import infrastructure.ISearchRepository;
import java.util.Date;
import javax.persistence.EntityManager;

public class SearchRepository implements ISearchRepository {

    private final EntityManager entityManager;

    public SearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(String origin, String destination, Date departureDate, int numberOfPassengers, Date timeCreated) {
        this.entityManager.getTransaction().begin();

        SearchLogEntity searchLogEntity = new SearchLogEntity();
        searchLogEntity.setOrigin(origin);
        searchLogEntity.setDestination(destination);
        searchLogEntity.setNumberOfPassengers(numberOfPassengers);
        searchLogEntity.setTimeCreated(timeCreated);
        searchLogEntity.setDepartureDate(departureDate);

        this.entityManager.persist(searchLogEntity);

        this.entityManager.getTransaction().commit();
    }

}
