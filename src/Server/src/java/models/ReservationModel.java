package models;

import java.util.List;

public class ReservationModel {

    private final ReservatorModel reservator;
    private final FlightModel flight;
    private final List<PassengerModel> passengers;

    public ReservationModel(ReservatorModel reservator, FlightModel flight, List<PassengerModel> passengers) {
        this.reservator = reservator;
        this.flight = flight;
        this.passengers = passengers;
    }

    public List<PassengerModel> getPassengers() {
        return passengers;
    }

    public FlightModel getFlight() {
        return flight;
    }

    public ReservatorModel getReservator() {
        return reservator;
    }
}
