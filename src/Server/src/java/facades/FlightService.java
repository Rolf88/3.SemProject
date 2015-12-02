package facades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.FlightModel;

public class FlightService {

    public List<FlightModel> findAllFlights() {
        List<FlightModel> flights = new ArrayList<>();

        return flights;
    }

    public List<FlightModel> findFlights(String iataOrigin, String iataDestination, Date departure) {
        List<FlightModel> flights = new ArrayList<>();

        return flights;
    }
}
