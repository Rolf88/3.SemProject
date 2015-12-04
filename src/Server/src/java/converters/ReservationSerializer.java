/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import models.PassengerModel;
import models.ReservationModel;

/**
 *
 * @author RolfMoikjær
 */
public class ReservationSerializer {

    private static JsonObject reservationToJsonObject(ReservationModel reservation) {
        JsonObject obj = new JsonObject();

        JsonArray passengerArr = new JsonArray();

        obj.addProperty("flightID", reservation.getFlight().getFlightID());
        obj.addProperty("Origin", reservation.getFlight().getOrigin());
        obj.addProperty("Destination", reservation.getFlight().getDestination());
        obj.addProperty("Date", reservation.getFlight().getDate().toString());
        obj.addProperty("FlightTime", reservation.getFlight().getTraveltime());
        obj.addProperty("numberOfSeats", reservation.getPassengers().size());
        obj.addProperty("ReserveeName", reservation.getReservator().getFirstname() + reservation.getReservator().getLastname());

        for (PassengerModel passenger : reservation.getPassengers()) {
            JsonObject pas = new JsonObject();

            pas.addProperty("firstName", passenger.getFirstname());
            pas.addProperty("lastName", passenger.getLastname());
            passengerArr.add(pas);
        }

        obj.add("Passengers", passengerArr);

        return obj;
    }

    public static String reservationToJson(ReservationModel reservation) {
        return new Gson().toJson(reservationToJsonObject(reservation));
    }

    public static String reservationToJson(List<ReservationModel> reservations) {
        JsonArray reservationArr = new JsonArray();
        for (ReservationModel reservationModel : reservations) {
            reservationArr.add(reservationToJsonObject(reservationModel));
        }

        return new Gson().toJson(reservationArr);
    }
}
