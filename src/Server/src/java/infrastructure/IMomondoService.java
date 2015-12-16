package infrastructure;

import java.util.List;
import models.AirlineInternalModel;
import models.AirportModel;
import models.HighscoreModel;
import models.PassengerModel;
import models.ReservateModel;

public interface IMomondoService {

    ReservateModel reservate(String baseApiUrl, String flightId, String reservatorUserId, List<PassengerModel> passengers);

    List<AirlineInternalModel> findFlights(String origin, String date, int numberOfPassengers);

    List<AirlineInternalModel> findFlights(String origin, String destination, String date, int numberOfPassengers);

    List<AirportModel> searchAirports(String name);

    List<HighscoreModel<String>> getDestinationHighscore(int size);
}
