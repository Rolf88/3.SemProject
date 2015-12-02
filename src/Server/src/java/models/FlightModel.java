package models;

public class FlightModel {
    
    private int numberOfSeats;
    private int numberOfSeatsLeft;
    private String flightNumber;

    public FlightModel(int numberOfSeats, int numberOfSeatsLeft, String flightNumber) {
        this.numberOfSeats = numberOfSeats;
        this.numberOfSeatsLeft = numberOfSeatsLeft;
        this.flightNumber = flightNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public int getNumberOfSeatsLeft() {
        return numberOfSeatsLeft;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setNumberOfSeatsLeft(int numberOfSeatsLeft) {
        this.numberOfSeatsLeft = numberOfSeatsLeft;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}