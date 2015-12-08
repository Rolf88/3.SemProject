package models;

public class PassengerModel {

    private final String firstname;
    private final String lastname;

    public PassengerModel(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
