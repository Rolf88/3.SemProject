package logic;

import entities.FlightEntity;
import entities.ReservationEntity;
import exceptions.NoFlightFoundException;
import exceptions.NotEnoughTicketsException;
import infrastructure.IFlightRepository;
import infrastructure.IFlightService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.AirportModel;
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
    public List<FlightModel> findFlights(String iataOrigin, String iataDestination, Date departure) throws NoFlightFoundException {
        if (iataDestination.equals(iataOrigin)) {
            throw new UnsupportedOperationException();
        }

        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, iataDestination, departure);

        if (flights.isEmpty()) {
            throw new NoFlightFoundException("No flights found");
        }

        return convertToFlightModels(flights);
    }

    @Override
    public ReservationModel reservate(String flightId, ReservatorModel reservator, List<PassengerModel> passengers) throws NotEnoughTicketsException, NoFlightFoundException {
        FlightEntity flight = this.flightRepository.getFlightById(flightId);

        if (flight == null) {
            throw new NoFlightFoundException("Flight not found");
        }

        int currentNumberOfPassengers = this.flightRepository.getNumberOfPassengers(flight.getId().intValue());

        if (currentNumberOfPassengers + passengers.size() > flight.getCapacity()) {
            throw new NotEnoughTicketsException("Flight is fully booked");
        }

        ReservationEntity source = this.flightRepository.createReservation(flight, reservator, passengers);

        FlightModel flightModel = convertToFlightModel(source.getFlight(), passengers.size());
        return new ReservationModel(new ReservatorModel(source.getFirstname(), source.getLastname(), source.getEmail(), source.getPhone()), flightModel, passengers);
    }

    @Override
    public List<FlightModel> findFlights(String iataOrigin, Date departure, int numberOfPassengers) throws NotEnoughTicketsException, NoFlightFoundException {
        List<FlightEntity> flights = flightRepository.findFlights(iataOrigin, departure, numberOfPassengers);
        List<FlightModel> fms = new ArrayList();

        if (flights.isEmpty()) {
            throw new NoFlightFoundException("No flights found");
        }

        for (FlightEntity flight : flights) {
            FlightModel fm = convertToFlightModel(flight, numberOfPassengers);
            fms.add(convertToFlightModel(flight, numberOfPassengers));
        }

        if (fms.size() <= 0) {
            throw new NotEnoughTicketsException("No flight with the amount of tickets you need");
        }

        return fms;
    }

    @Override
    public List<FlightModel> findFlights(String iataOrigin, String iataDestination, Date departure, int tickets) throws NotEnoughTicketsException, NoFlightFoundException {
        List<FlightEntity> flights = flightRepository.findFlights(iataOrigin, iataDestination, departure, tickets);
        List<FlightModel> fms = new ArrayList();

        if (flights.size() <= 0) {
            throw new NoFlightFoundException("No flights found");
        }

        for (FlightEntity flight : flights) {
            FlightModel fm = convertToFlightModel(flight, tickets);
            fms.add(convertToFlightModel(flight, tickets));
        }

        if (fms.size() <= 0) {
            throw new NotEnoughTicketsException("No flight with the amount of tickets you need");
        }

        return fms;
    }

    @Override
    public FlightModel getFlightById(String flightId) {
        FlightEntity flight = this.flightRepository.getFlightById(flightId);
        FlightModel flightModel = convertToFlightModel(flight, 1);

        return flightModel;
    }

    private List<FlightModel> convertToFlightModels(List<FlightEntity> source) {
        List<FlightModel> flights = new ArrayList<>();

        for (FlightEntity flight : source) {
            flights.add(convertToFlightModel(flight, 1));
        }

        return flights;
    }

    private FlightModel convertToFlightModel(FlightEntity flight, int numberOfPassengers) {
        return new FlightModel(flight.getDeparture(), numberOfPassengers,
                flight.getPrice() * numberOfPassengers, flight.getFlightId(), flight.getTravelTime(),
                new AirportModel(flight.getDestination().getIataCode(), flight.getDestination().getName()), new AirportModel(flight.getOrigin().getIataCode(), flight.getOrigin().getName()));
    }
}
