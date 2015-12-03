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
        return convertToFlightModels(this.flightRepository.findAllFlights());
    }

    @Override
    public List<FlightModel> findFlights(String iataOrigin, String iataDestination, Date departure) {
        if (iataDestination.equals(iataOrigin)) {
            throw new UnsupportedOperationException();
        }

        return convertToFlightModels(this.flightRepository.findFlights(iataOrigin, iataDestination, departure));
    }

    @Override
    public ReservationModel reservate(int flightId, ReservatorModel reservator, List<PassengerModel> passengers) throws NotEnoughTicketsException, NoFlightFoundException {
        FlightEntity flight = this.flightRepository.getFlightById(flightId);

        if (flight == null) {
            throw new NoFlightFoundException("Flight not found");
        }

        ReservationModel reservation = new ReservationModel(reservator);

        return reservation;
    }

    @Override
    public List<FlightModel> findFlights(String iataOrigin, Date departure, int numberOfPassengers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<FlightModel> convertToFlightModels(List<FlightEntity> source) {
        List<FlightModel> flights = new ArrayList<>();

        for (FlightEntity flight : source) {
            flights.add(new FlightModel(flight.getCapacity(), 10, flight.getId().toString()));
        }

        return flights;
    }
}
