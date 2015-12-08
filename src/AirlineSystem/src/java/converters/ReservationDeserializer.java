/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import models.PassengerModel;
import models.ReservatorModel;

/**
 *
 * @author RolfMoikjær
 */
public class ReservationDeserializer {

    private final JsonObject object;

    public ReservationDeserializer(String json) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        object = element.getAsJsonObject();
    }

    public ReservatorModel getReservator() {

        ReservatorModel reservator = new ReservatorModel(object.get("ReserveeName").getAsString(), object.get("ReserveeName").getAsString(), object.get("ReserveeEmail").getAsString(), object.get("ReservePhone").getAsString());

        return reservator;
    }

    public String getFlightID() {

        String flightID = object.get("flightID").getAsString();

        return flightID;
    }

    public List<PassengerModel> getPassengers() {
        List<PassengerModel> passengers = new ArrayList<>();
        JsonArray jsonArray = object.get("Passengers").getAsJsonArray();

        int arrLength = jsonArray.size();

        for (int i = 0; i < arrLength; i++) {
            JsonObject passengerObject = jsonArray.get(i).getAsJsonObject();

            PassengerModel passenger = new PassengerModel(passengerObject.get("firstName").getAsString(), passengerObject.get("lastName").getAsString());
            passengers.add(passenger);
        }
        return passengers;
    }
}
