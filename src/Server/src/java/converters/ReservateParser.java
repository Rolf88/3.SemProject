package converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.UserEntity;
import java.util.List;
import models.PassengerModel;

public class ReservateParser {

    public static String serialize(String flightId, UserEntity reservator, List<PassengerModel> passengers) {
        JsonObject obj = new JsonObject();
        obj.addProperty("flightID", flightId);
        obj.addProperty("numberOfSeats", passengers.size());
        obj.addProperty("ReserveeName", reservator.getFirstname() + " " + reservator.getLastname());
        obj.addProperty("ReservePhone", reservator.getPhone());
        obj.addProperty("ReserveeEmail", reservator.getEmail());

        JsonArray passengerObjects = new JsonArray();
        for (PassengerModel passenger : passengers) {
            JsonObject passengerObject = new JsonObject();

            passengerObject.addProperty("firstName", passenger.getFirstname());
            passengerObject.addProperty("lastName", passenger.getLastname());
            passengerObjects.add(passengerObject);
        }

        obj.add("Passengers", passengerObjects);

        return obj.toString();
    }
}
