package converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import models.FlightModel;

public class FlightConverter {

    static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public static String serialize(List<FlightModel> flights) {
        JsonArray arr = new JsonArray();

        for (FlightModel flight : flights) {
            JsonObject flightObject = new JsonObject();
            flightObject.addProperty("date", gson.toJson(flight.getDate()));
            flightObject.addProperty("numberOfSeats", flight.getNumberOfPassengers());
            flightObject.addProperty("totalPrice", flight.getTotalPrice());
            flightObject.addProperty("flightID", flight.getFlightID());
            flightObject.addProperty("traveltime", flight.getTraveltime());
            flightObject.addProperty("destination", flight.getDestination().getIataCode());
            flightObject.addProperty(("origin"), flight.getOrigin().getIataCode());

            arr.add(flightObject);
        }

        return gson.toJson(arr);
    }

}
