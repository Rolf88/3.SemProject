package infrastructure;

import models.ReservationModel;

public interface IReservationRepository {

    void add(Long userId, ReservationModel reservation);
}
