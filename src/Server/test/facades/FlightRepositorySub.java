/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.FlightEntity;
import entity.ReservationEntity;
import infrastructure.IFlightRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.PassengerModel;
import models.ReservatorModel;
import utils.FlightBuilder;

/**
 *
 * @author RolfMoikjær
 */
public class FlightRepositorySub implements IFlightRepository {

    private List<FlightEntity> flightsDataSource = new ArrayList<>();

    public FlightRepositorySub() {
        flightsDataSource.add(FlightBuilder.create()
                .setFlight(231, new Date(116, 0, 1), 100, 230)
                .withAirline("Flyv Flyv")
                .withDestination(981, "QAR", "Rotterdam Airport")
                .withOrigin(982, "CPH", "Copenhagen Airport")
                .withReservation("Gert", "Jørgensen", "Gert@Jørgensen.dk", "45879556")
                .build());

        flightsDataSource.add(FlightBuilder.create()
                .setFlight(2345, new Date(116, 0, 1), 100, 230)
                .withAirline("Flyv Flyv")
                .withDestination(981, "QAR", "Rotterdam Airport")
                .withOrigin(982, "CPH", "Copenhagen Airport")
                .withReservation("Brian", "Madsen", "brian@madsen.dk", "598494")
                .build());

        flightsDataSource.add(FlightBuilder.create()
                .setFlight(235, new Date(116, 0, 1), 100, 230)
                .withAirline("Flyv Flyv")
                .withDestination(981, "QAR", "Rotterdam Airport")
                .withOrigin(982, "CPH", "Copenhagen Airport")
                .withReservation("Kim", "Larsen", "Kim@Larsen.dk", "342353")
                .build());

        flightsDataSource.add(FlightBuilder.create()
                .setFlight(234, new Date(116, 0, 1), 2, 230)
                .withAirline("Flyv Flyv")
                .withDestination(981, "QAR", "Rotterdam Airport")
                .withOrigin(982, "CPH", "Copenhagen Airport")
                .withReservation("Kim", "Larsen", "Kim@Larsen.dk", "342353")
                .build());
    }

    @Override
    public List<FlightEntity> findAllFlights() {
        return flightsDataSource;
    }

    @Override
    public List<FlightEntity> findFlights(String iataOrigin, String iataDestination, Date departure) {
        List<FlightEntity> flights = new ArrayList<>();

        for (FlightEntity flight : flightsDataSource) {
            if (flight.getDestination().getIataCode().equals(iataDestination) && flight.getOrigin().getIataCode().equals(iataOrigin)) {
                flights.add(flight);
            }
        }

        return flights;
    }

    @Override
    public List<FlightEntity> findFlights(String iataOrigin, Date departure, int numberOfPassengers) {
        List<FlightEntity> flights = new ArrayList<>();

        for (FlightEntity flight : flightsDataSource) {
            if (flight.getOrigin().getIataCode().equals(iataOrigin)) {
                flights.add(flight);
            }
        }

        return flights;
    }

    @Override
    public ReservationEntity createReservation(FlightEntity flight, ReservatorModel reservator, List<PassengerModel> passengers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FlightEntity getFlightById(int flightId) {

        for (FlightEntity flight : flightsDataSource) {
            if (flight.getId().equals((long) flightId)) {
                return flight;
            }
        }

        return null;
    }

    @Override
    public int getNumberOfPassengers(int flightId) {
        FlightEntity flight = getFlightById(flightId);

        int passengers = 0;
        for (ReservationEntity reservation : flight.getReservations()) {
            passengers = reservation.getPasssengers().size();
        }

        return passengers;
    }
}
