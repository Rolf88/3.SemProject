package facades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.FlightModel;
import models.PassengerModel;
import models.ReservationModel;
import models.ReservatorModel;

public class FlightService {

    public List<FlightModel> findAllFlights() {
        List<FlightModel> flights = new ArrayList<>();

        return flights;
    }

    public List<FlightModel> findFlights(String iataOrigin, String iataDestination, Date departure) {
        List<FlightModel> flights = new ArrayList<>();

        return flights;
    }

    public ReservationModel reservate(int flightId, ReservatorModel reservator, List<PassengerModel> passengers) {
        ReservationModel reservation = new ReservationModel();

        return reservation;
    }
}
