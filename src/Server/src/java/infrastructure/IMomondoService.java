package infrastructure;

import java.util.List;
import models.AirlineInternalModel;
import models.ReservationModel;

public interface IMomondoService {

    ReservationModel reservate(String flightId, String userId);

    List<AirlineInternalModel> findFlights(String origin, String date, int numberOfPassengers);

    List<AirlineInternalModel> findFlights(String origin, String destination, String date, int numberOfPassengers);
}
