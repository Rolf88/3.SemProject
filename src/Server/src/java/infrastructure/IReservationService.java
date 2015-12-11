package infrastructure;

import entity.UserEntity;
import java.util.List;
import models.PassengerModel;
import models.ReservateModel;
import models.ReservationModel;

public interface IReservationService {

    ReservateModel reservate(String baseApiUrl, String flightId, UserEntity reservatorUser, List<PassengerModel> passengers);

    List<ReservationModel> getByUserId(Long userId);

    List<ReservationModel> findAll();
}
