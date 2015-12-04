/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.ReservationModel;

/**
 *
 * @author RolfMoikjær
 */
public class ReservationSerializer {

    private static JsonObject reservationToJsonObject(ReservationModel reservation) {
        JsonObject obj = new JsonObject();
        obj.addProperty("flightID", reservation.getFlight().getFlightID());
        obj.addProperty("Origin", reservation.getFlight().getOrigin());
        obj.addProperty("Destination", reservation.getFlight().getDestination());
        obj.addProperty("Date", reservation.getFlight().getDate().toString());
        obj.addProperty("FlightTime", reservation.getFlight().getTraveltime());
        obj.addProperty("numberOfSeats", reservation.getPassengers().size());
        obj.addProperty("ReserveeName", reservation.getReservator().getFirstname() + reservation.getReservator().getLastname());

        //vil sikkert ikke virke husk at lave om!!!!!!
        obj.add("Passengers", (JsonElement) reservation.getPassengers());

        return obj;
    }
}
