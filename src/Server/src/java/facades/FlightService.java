package facades;

import exceptions.NoFlightFoundException;
import exceptions.NotEnoughTicketsException;
import infrastructure.IFlightService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.FlightModel;
import models.PassengerModel;
import models.ReservationModel;
import models.ReservatorModel;

public class FlightService implements IFlightService{

    public List<FlightModel> findAllFlights() {
        List<FlightModel> flights = new ArrayList<>();

        return flights;
    }

    public List<FlightModel> findFlights(String iataOrigin, String iataDestination, Date departure) {
        List<FlightModel> flights = new ArrayList<>();

        return flights;
    }

    public ReservationModel reservate(int flightId, ReservatorModel reservator, List<PassengerModel> passengers) {
        ReservationModel reservation = new ReservationModel(reservator);

        return reservation;
    }

    @Override
    public List<FlightModel> findFlights(String iataOrigin, Date departure, int tickets) throws NotEnoughTicketsException, NoFlightFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
