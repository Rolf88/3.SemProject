package models;

import java.util.Date;

public class FlightModel {

    private final Date date;
    private final int numberOfSeats;
    private String flightID;
    private final int traveltime;
    private int totalPrice;
    private final String destination;
    private final String origin;

    public FlightModel(String flightID, Date date, int numberOfSeats, int traveltime, String destination, String origin) {
        this.date = date;
        this.numberOfSeats = numberOfSeats;
        this.flightID = flightID;
        this.traveltime = traveltime;
        this.destination = destination;
        this.origin = origin;
    }

    public FlightModel(Date date, int numberOfSeats, String flightID, int traveltime, int totalPrice, String destination, String origin) {
        this.date = date;
        this.numberOfSeats = numberOfSeats;
        this.flightID = flightID;
        this.traveltime = traveltime;
        this.totalPrice = totalPrice;
        this.destination = destination;
        this.origin = origin;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfPassengers() {
        return numberOfSeats;
    }

    public String getFlightID() {
        return flightID;
    }

    public int getTraveltime() {
        return traveltime;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}
