package entity;

import infrastructure.ISearchRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.HighscoreModel;

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

    @Override
    public List<HighscoreModel<String>> getTopDestination(int size) {
        Query query = this.entityManager.createQuery("SELECT s.destination as destination, COUNT(s) AS numbers FROM SearchLog s "
                + "GROUP BY s.destination "
                + "ORDER BY COUNT(s) DESC")
                .setMaxResults(size);

        List<HighscoreModel<String>> results = new ArrayList<>();

        for (Object[] response : (List<Object[]>) query.getResultList()) {
            results.add(new HighscoreModel<>((String) response[0], ((Number) response[1]).intValue()));
        }

        return results;
    }

}
