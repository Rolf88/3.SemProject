package models;

public class ReservationModel {

    private final ReservatorModel reservator;

    public ReservationModel(ReservatorModel reservator) {
        this.reservator = reservator;
    }

    public ReservatorModel getReservator() {
        return reservator;
    }
}
