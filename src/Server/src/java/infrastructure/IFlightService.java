package infrastructure;

import exceptions.NoFlightFoundException;
import exceptions.NotEnoughTicketsException;
import java.util.Date;
import java.util.List;
import models.FlightModel;
import models.PassengerModel;
import models.ReservationModel;
import models.ReservatorModel;

public interface IFlightService {

    List<FlightModel> findAllFlights();

    List<FlightModel> findFlights(String iataOrigin, String iataDestination, Date departure);
    
    List<FlightModel> findFlights(String iataOrigin, Date departure, int tickets) throws NotEnoughTicketsException, NoFlightFoundException;

    ReservationModel reservate(int flightId, ReservatorModel reservator, List<PassengerModel> passengers);
}
