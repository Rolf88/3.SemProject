package entity;

import infrastructure.IReservationRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.PassengerModel;
import models.ReservateModel;

public class ReservationRepository implements IReservationRepository {

    private final EntityManager entityManager;

    public ReservationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Long userId, ReservateModel reservation) {
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

    @Override
    public List<ReservationEntity> findAll() {
        Query query = entityManager.createQuery("SELECT r FROM Reservation r");

        return query.getResultList();
    }

    @Override
    public List<ReservationEntity> getByUserId(Long userId) {
        Query query = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.user.id = :id");
        query.setParameter("id", userId);

        return query.getResultList();
    }

}
