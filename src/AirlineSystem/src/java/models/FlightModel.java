package models;

import java.util.Date;

public class FlightModel {

    private Date date;
    private int numberOfSeats;
    private double totalPrice;
    private String flightID;
    private int traveltime;
    private AirportModel destination;
    private AirportModel origin;

    public FlightModel(Date date, int numberOfSeats, double totalPrice, String flightID, int traveltime, AirportModel destination, AirportModel origin) {
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

    public AirportModel getDestination() {
        return destination;
    }

    public AirportModel getOrigin() {
        return origin;
    }
}
