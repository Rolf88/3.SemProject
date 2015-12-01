package models;

public class ReservationModel {

    private final ReservationModel reservator;

    public ReservationModel(ReservationModel reservator) {
        this.reservator = reservator;
    }

    public ReservationModel getReservator() {
        return reservator;
    }
}
