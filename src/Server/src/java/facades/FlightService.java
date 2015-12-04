package facades;

import entity.FlightEntity;
import entity.ReservationEntity;
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

        int currentNumberOfPassengers = this.flightRepository.getNumberOfPassengers(flight.getId().intValue());

        if (currentNumberOfPassengers + passengers.size() > flight.getCapacity()) {
            throw new NotEnoughTicketsException("Flight is fully booked");
        }

        ReservationEntity source = this.flightRepository.createReservation(flight, reservator, passengers);

        return new ReservationModel(new ReservatorModel(source.getFirstname(), source.getLastname(), source.getEmail(), source.getPhone()), null, null);
    }

    @Override
    public List<FlightModel> findFlights(String iataOrigin, Date departure, int numberOfPassengers) throws NotEnoughTicketsException, NoFlightFoundException{
        List<FlightEntity> flights = flightRepository.findFlights(iataOrigin, departure, numberOfPassengers);
        List<FlightModel> fms = new ArrayList();

        if(flights.size() <= 0)
            throw new NoFlightFoundException("No flights found");
        
        for (FlightEntity flight : flights) {
            int seatsLeft = flight.getCapacity() - flight.getReservations().size();
            if(flight.getCapacity() - flight.getReservations().size() >= numberOfPassengers){
                FlightModel fm = new FlightModel();
                
                fm.setDestination(flight.getDestination().getIataCode());
                fm.setNumberOfSeats(flight.getCapacity());
                fm.setNumberOfSeats(seatsLeft);
                fm.setDate(departure);
                
                fms.add(fm);
            }
        }

        if(fms.size() <= 0)
            throw new NotEnoughTicketsException("No flight with the amount of tickets you need");

        return fms;
    }

    private List<FlightModel> convertToFlightModels(List<FlightEntity> source) {
        List<FlightModel> flights = new ArrayList<>();

        for (FlightEntity flight : source) {
            flights.add(new FlightModel(flight.getCapacity() - flight.getReservations().size(), flight.getId().toString()));
        }

        return flights;
    }
}
