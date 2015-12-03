package facades;

import entity.FlightEntity;
import exceptions.NoFlightFoundException;
import exceptions.NotEnoughTicketsException;
import infrastructure.IFlightRepository;
import infrastructure.IFlightService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.FlightModel;
import models.PassengerModel;
import models.ReservationModel;
import models.ReservatorModel;

public class FlightService implements IFlightService {

    private final IFlightRepository flightRepository;

    public FlightService(IFlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<FlightModel> findAllFlights() {
        List<FlightModel> flights = new ArrayList<>();

        for (FlightEntity flight : this.flightRepository.findAllFlights()) {
            flights.add(new FlightModel(flight.getCapacity(), 10, flight.getId().toString()));
        }

        return flights;
    }

    @Override
    public List<FlightModel> findFlights(String iataOrigin, String iataDestination, Date departure) {
        if (iataDestination.equals(iataOrigin)) {
            throw new UnsupportedOperationException();
        }

        List<FlightModel> flights = new ArrayList<>();

        for (FlightEntity flight : this.flightRepository.findFlights(iataOrigin, iataDestination, departure)) {
            flights.add(new FlightModel(flight.getCapacity(), 10, flight.getId().toString()));
        }

        return flights;
    }

    @Override
    public ReservationModel reservate(int flightId, ReservatorModel reservator, List<PassengerModel> passengers) {
        FlightEntity flight = this.flightRepository.getFlightById(flightId);

        if (flight == null) {
            throw new NullPointerException("Flight not found");
        }

        ReservationModel reservation = new ReservationModel(reservator);

        return reservation;
    }

    @Override
    public List<FlightModel> findFlights(String iataOrigin, Date departure, int numberOfPassengers) throws NotEnoughTicketsException, NoFlightFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
