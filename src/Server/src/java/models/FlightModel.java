package models;

import java.util.Date;

public class FlightModel {

    private Date date;
    private int numberOfSeats;
    private double totalPrice;
    private String flightID;
    private int traveltime;
    private String destination;
    private String origin;

    public FlightModel(Date date, int numberOfSeats, double totalPrice, String flightID, int traveltime, String destination, String origin) {
        this.date = date;
        this.numberOfSeats = numberOfSeats;
        this.totalPrice = totalPrice;
        this.flightID = flightID;
        this.traveltime = traveltime;
        this.destination = destination;
        this.origin = origin;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfPassengers() {
        return numberOfSeats;
    }

    public double getTotalPrice() {
        return totalPrice;
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
}
