package models;

public class ReservationModel {

    private final ReservationModel reservator;

    public ReservationModel(ReservationModel reservator) {
        this.reservator = reservator;
    }

    public ReservationModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ReservationModel getReservator() {
        return reservator;
    }
}
