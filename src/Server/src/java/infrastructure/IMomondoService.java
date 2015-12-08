package infrastructure;

import java.util.List;
import models.AirlineInternalModel;
import models.PassengerModel;
import models.ReservationModel;

public interface IMomondoService {

    ReservationModel reservate(String baseApiUrl, String flightId, String reservatorUserId, List<PassengerModel> passengers);

    List<AirlineInternalModel> findFlights(String origin, String date, int numberOfPassengers);

    List<AirlineInternalModel> findFlights(String origin, String destination, String date, int numberOfPassengers);
}
