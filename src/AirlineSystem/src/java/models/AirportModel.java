package models;

public class AirportModel {

    private String iataCode;
    private String name;

    public AirportModel(String iataCode, String name) {
        this.iataCode = iataCode;
        this.name = name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getName() {
        return name;
    }

}
