/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import models.PassengerModel;
import models.ReservationModel;

/**
 *
 * @author RolfMoikjær
 */
public class ReservationSerializer {

    private static JsonObject reservationToJsonObject(ReservationModel reservation) throws ParseException {
        JsonObject obj = new JsonObject();

        JsonArray passengerArr = new JsonArray();

        String date1 = convertToJsonDate(reservation.getFlight().getDate());

        obj.addProperty("flightID", reservation.getFlight().getFlightID());
        obj.addProperty("Origin", reservation.getFlight().getOrigin().getName() + " (" + reservation.getFlight().getOrigin().getIataCode() + ")");
        obj.addProperty("Destination", reservation.getFlight().getDestination().getName() + " (" + reservation.getFlight().getDestination().getIataCode() + ")");
        obj.addProperty("Date", date1);
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

    public static String reservationToJson(ReservationModel reservation) throws ParseException {
        return new Gson().toJson(reservationToJsonObject(reservation));
    }

    public static String reservationToJson(List<ReservationModel> reservations) throws ParseException {
        JsonArray reservationArr = new JsonArray();
        for (ReservationModel reservationModel : reservations) {
            reservationArr.add(reservationToJsonObject(reservationModel));
        }

        return new Gson().toJson(reservationArr);
    }

    private static String convertToJsonDate(Date date) throws ParseException {

        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return sdfISO.format(date);

    }
}
