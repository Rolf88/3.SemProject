/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.FlightApiUrls;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author RolfMoikjær
 */
public class FlightApiUrlsFacade {

    private final EntityManager entityManager;

    public FlightApiUrlsFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<FlightApiUrls> getFlightApiUrls() {
        Query createQuery = this.entityManager.createQuery("SELECT u FROM FlightApiUrls u");

        return createQuery.getResultList();
    }

}
