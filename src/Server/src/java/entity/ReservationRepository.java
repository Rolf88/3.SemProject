package entity;

import infrastructure.IReservationRepository;
import javax.persistence.EntityManager;
import models.PassengerModel;
import models.ReservationModel;

public class ReservationRepository implements IReservationRepository {

    private final EntityManager entityManager;

    public ReservationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Long userId, ReservationModel reservation) {
        this.entityManager.getTransaction().begin();

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setFlightId(reservation.getFlight().getFlightID());
        reservationEntity.setDestination(reservation.getFlight().getDestination());
        reservationEntity.setOrigin(reservation.getFlight().getOrigin());
        reservationEntity.setFlightTime(reservation.getFlight().getTraveltime());
        reservationEntity.setDepartureDate(reservation.getFlight().getDate());

        UserEntity user = this.entityManager.find(UserEntity.class, userId);
        reservationEntity.setUser(user);

        for (PassengerModel passenger : reservation.getPassengers()) {
            reservationEntity.addPassenger(passenger.getFirstname(), passenger.getLastname());
        }
        
        this.entityManager.persist(reservationEntity);

        this.entityManager.getTransaction().commit();
    }

}
