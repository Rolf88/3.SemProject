package models;

public class AirportModel {

    private String city;
    private String country;
    private String code;

    public AirportModel(String city, String country, String code) {
        this.city = city;
        this.country = country;
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getIataCode() {
        return code;
    }

}
