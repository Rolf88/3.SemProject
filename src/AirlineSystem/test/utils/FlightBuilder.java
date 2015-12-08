package utils;

import entities.AirlineEntity;
import entities.AirportEntity;
import entities.FlightEntity;
import entities.ReservationEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightBuilder {

    FlightEntity flightEntity;

    public static FlightBuilder create() {
        return new FlightBuilder();
    }
    private AirportEntity destination;
    private AirportEntity origin;
    private AirlineEntity airline;
    private List<ReservationEntity> reservations = new ArrayList<>();

    public FlightBuilder setFlight(int id, Date departure, int capacity, double price, String flightId) {
        this.flightEntity = new FlightEntity();
        this.flightEntity.setId(Long.valueOf(id));
        this.flightEntity.setDeparture(departure);
        this.flightEntity.setCapacity(capacity);
        this.flightEntity.setPrice(price);
        this.flightEntity.setFlightId(flightId);

        return this;
    }

    public FlightBuilder withDestination(int id, String iataCode, String name) {
        this.destination = new AirportEntity();
        this.destination.setId(Long.valueOf(id));
        this.destination.setIataCode(iataCode);
        this.destination.setName(name);

        return this;
    }

    public FlightBuilder withOrigin(int id, String iataCode, String name) {
        this.origin = new AirportEntity();
        this.origin.setId(Long.valueOf(id));
        this.origin.setIataCode(iataCode);
        this.origin.setName(name);

        return this;
    }

    public FlightEntity build() {
        this.flightEntity.setOrigin(origin);
        this.flightEntity.setDestination(destination);
        this.flightEntity.setAirline(this.airline);

        for (ReservationEntity reservation : this.reservations) {
            this.flightEntity.getReservations().add(reservation);
        }

        return this.flightEntity;
    }

    public FlightBuilder withAirline(String name) {
        this.airline = new AirlineEntity();
        this.airline.setName(name);

        return this;
    }

    public FlightBuilder withReservation(String firstName, String lastName, String email, String phone) {
        ReservationEntity reservation = new ReservationEntity();
        reservation.setFirstname(firstName);
        reservation.setLastname(lastName);
        reservation.setEmail(email);
        reservation.setPhone(phone);

        this.reservations.add(reservation);

        return this;
    }
}
