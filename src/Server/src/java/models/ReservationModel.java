package models;

import java.util.List;

public class ReservationModel {

    private final FlightModel flight;
    private final List<PassengerModel> passengers;

    public ReservationModel(FlightModel flight, List<PassengerModel> passengers) {
        this.flight = flight;
        this.passengers = passengers;
    }

    public List<PassengerModel> getPassengers() {
        return passengers;
    }

    public FlightModel getFlight() {
        return flight;
    }
}
