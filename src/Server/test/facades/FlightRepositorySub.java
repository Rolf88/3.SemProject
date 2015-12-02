/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.AirlineEntity;
import entity.AirportEntity;
import entity.FlightEntity;
import entity.ReservationEntity;
import infrastructure.IFlightRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.PassengerModel;
import models.ReservatorModel;

/**
 *
 * @author RolfMoikjær
 */
public class FlightRepositorySub implements IFlightRepository {

    @Override
    public List<FlightEntity> findAllFlights() {
        Long id = 23l;
        Date departure = new Date(116, 00, 01, 06, 00, 00);
        int capacity = 110;
        double price = 230;
        AirlineEntity airline = new AirlineEntity(25l, "Flyv Flyv");
        AirportEntity origin = new AirportEntity(98l, "CPH", "Copenhagen Airport");
        AirportEntity destination = new AirportEntity(98l, "QAR", "Rotterdam Airport");
        List<ReservationEntity> reservations = new ArrayList<>();
        reservations.add(new ReservationEntity(46l, "Gert", "Jørgensen", "Gert@Jørgensen.dk", "45879556"));

        List<FlightEntity> flights = new ArrayList<>();
        flights.add(new FlightEntity(id, departure, capacity, price, airline, origin, destination, reservations));

        return flights;
    }

    @Override
    public List<FlightEntity> findFlights(String iataOrigin, String iataDestination, Date departure) {
        Long id = 23l;
        int capacity = 110;
        double price = 230;
        AirlineEntity airline = new AirlineEntity(25l, "Flyv Flyv");
        AirportEntity origin = new AirportEntity(98l, iataOrigin, "Copenhagen Airport");
        AirportEntity destination = new AirportEntity(98l, iataDestination, "Rotterdam Airport");
        List<ReservationEntity> reservations = new ArrayList<>();
        reservations.add(new ReservationEntity(46l, "Gert", "Jørgensen", "Gert@Jørgensen.dk", "45879556"));

        List<FlightEntity> flights = new ArrayList<>();
        flights.add(new FlightEntity(id, departure, capacity, price, airline, origin, destination, reservations));

        return flights;
    }

    @Override
    public List<FlightEntity> findFlights(String iataOrigin, Date departure, int numberOfPassengers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReservationEntity createReservation(FlightEntity flight, ReservatorModel reservator, List<PassengerModel> passengers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FlightEntity getFlightById(int flightId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
