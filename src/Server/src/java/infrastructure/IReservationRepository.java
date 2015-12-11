package infrastructure;

import entity.ReservationEntity;
import java.util.List;
import models.ReservateModel;

public interface IReservationRepository {

    List<ReservationEntity> findAll();
    
    List<ReservationEntity> getByUserId(Long userId);
    
    void add(Long userId, ReservateModel reservation);
}
