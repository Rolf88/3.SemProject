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

    List<FlightEntity> findFlights(String iataOrigin, Date departure, int numberOfPassengers);

    ReservationEntity createReservation(FlightEntity flight, ReservatorModel reservator, List<PassengerModel> passengers);

    FlightEntity getFlightById(int flightId);

    FlightEntity getFlightById(String flightId);

    int getNumberOfPassengers(int flightId);
}
