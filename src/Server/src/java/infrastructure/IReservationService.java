package infrastructure;

import entity.UserEntity;
import java.util.List;
import models.PassengerModel;
import models.ReservationModel;

public interface IReservationService {

    ReservationModel reservate(String baseApiUrl, String flightId, UserEntity reservatorUser, List<PassengerModel> passengers);
}
