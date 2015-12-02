package infrastructure;

import entity.FlightEntity;
import entity.ReservationEntity;
import java.util.Date;
import java.util.List;
import models.PassengerModel;
import models.ReservatorModel;

public interface IFlightRepository {

    List<FlightEntity> findAllFlights();

    List<FlightEntity> findFlights(String iataOrigin, String iataDestination, Date departure);

    ReservationEntity createReservation(int flightId, ReservatorModel reservator, List<PassengerModel> passengers);
}
