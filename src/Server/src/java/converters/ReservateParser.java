package converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.UserEntity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.FlightModel;
import models.PassengerModel;
import models.ReservateModel;

public class ReservateParser {

    private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

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

    public static ReservateModel deserialize(String json) throws ParseException {
        JsonParser parser = new JsonParser();

        JsonObject root = parser.parse(json).getAsJsonObject();

        FlightModel flight = deserializeFlight(root);
        List<PassengerModel> passengers = deserializePassengers(root);
        return new ReservateModel(flight, passengers);
    }

    private static List<PassengerModel> deserializePassengers(JsonObject root) {
        List<PassengerModel> passengers = new ArrayList<>();
        for (JsonElement passengerElement : root.get("Passengers").getAsJsonArray()) {
            JsonObject passengerObject = passengerElement.getAsJsonObject();

            passengers.add(new PassengerModel(passengerObject.get("firstName").getAsString(), passengerObject.get("lastName").getAsString()));
        }

        return passengers;
    }

    private static FlightModel deserializeFlight(JsonObject root) throws ParseException {
        Date departureDate = dateFormatter.parse(root.get("Date").getAsString());
        int numberOfSeats = root.get("numberOfSeats").getAsInt();
        String flightId = root.get("flightID").getAsString();
        int flightTime = root.get("FlightTime").getAsInt();
        String origin = root.get("Origin").getAsString();
        String destination = root.get("Destination").getAsString();

        return new FlightModel(flightId, departureDate, numberOfSeats, flightTime, destination, origin, 0);
    }
}
