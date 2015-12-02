package infrastructure;

import entity.FlightEntity;
import java.util.List;

public interface IFlightRepository {

    public List<FlightEntity> findAllFlights();

}
