package models;

import java.util.List;

public class AirlineModel {
    
    private String airline;
    private List<FlightModel> flights;

    public AirlineModel() {
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public List<FlightModel> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightModel> flights) {
        this.flights = flights;
    }
    
    
    
}
