package models;

import java.util.List;

public class AirlineInternalModel {

    private String airline;    
    private String url;
    private List<FlightModel> flights;

    public AirlineInternalModel() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
