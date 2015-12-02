package infrastructure;

import entity.FlightEntity;
import java.util.Date;
import java.util.List;

public interface IFlightRepository {

    public List<FlightEntity> findAllFlights();

    List<FlightEntity> findFlights(String iataOrigin, String iataDestination, Date departure);

}
