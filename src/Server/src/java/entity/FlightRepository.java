package entity;

import infrastructure.IFlightRepository;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import models.PassengerModel;
import models.ReservatorModel;

public class FlightRepository implements IFlightRepository {

    private final EntityManager entityManager;

    public FlightRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<FlightEntity> findAllFlights() {
        return entityManager.createQuery("SELECT f FROM Flight f").getResultList();
    }

    @Override
    public List<FlightEntity> findFlights(String iataOrigin, String iataDestination, Date departure) {
        final String jpa = "SELECT f FROM Flight f "
                + "INNER JOIN Airport origin "
                + "INNER JOIN Airport destination "
                + "WHERE origin.iataCode = :iataOrigin AND destination.iataCode = :iataDestination AND f.departure BETWEEN :departureFrom AND :departureTo";

        Query query = entityManager.createQuery(jpa);

        query.setParameter("iataOrigin", iataOrigin);
        query.setParameter("iataDestination", iataDestination);
        query.setParameter("departureFrom", departure, TemporalType.TIMESTAMP);

        Date nextDate = new Date(departure.getTime() + 1 * 24 * 60 * 60 * 1000);
        query.setParameter("departureTo", nextDate, TemporalType.DATE);

        return query.getResultList();
    }

    @Override
    public List<FlightEntity> findFlights(String iataOrigin, Date departure, int numberOfPassengers) {
        final String jpa = "SELECT f FROM Flight f "
                + "INNER JOIN Airport origin "
                + "WHERE origin.iataCode = :iataOrigin AND f.departure BETWEEN :departureFrom AND :departureTo";

        Query query = entityManager.createQuery(jpa);

        query.setParameter("iataOrigin", iataOrigin);
        query.setParameter("departureFrom", departure, TemporalType.TIMESTAMP);

        Date nextDate = new Date(departure.getTime() + 1 * 24 * 60 * 60 * 1000);
        query.setParameter("departureTo", nextDate, TemporalType.DATE);

        return query.getResultList();
    }

    @Override
    public ReservationEntity createReservation(FlightEntity flight, ReservatorModel reservator, List<PassengerModel> passengers) {
        ReservationEntity reservation = new ReservationEntity();

        reservation.setFlight(flight);
        reservation.setFirstname(reservator.getFirstname());
        reservation.setLastname(reservator.getLastname());
        reservation.setEmail(reservator.getEmail());
        reservation.setPhone(reservator.getPhone());

        List<PassengerEntity> passengerEntities = reservation.getPasssengers();
        for (PassengerModel passenger : passengers) {
            PassengerEntity passengerEntity = new PassengerEntity();
            passengerEntity.setFirstname(passenger.getFirstname());
            passengerEntity.setLastname(passenger.getLastname());

            passengerEntities.add(passengerEntity);
        }

        entityManager.getTransaction().begin();
        entityManager.persist(reservation);
        entityManager.getTransaction().commit();

        return reservation;
    }

    @Override
    public FlightEntity getFlightById(int flightId) {
        return this.entityManager.find(FlightEntity.class, (long) flightId);
    }

    @Override
    public int getNumberOfPassengers(int flightId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
