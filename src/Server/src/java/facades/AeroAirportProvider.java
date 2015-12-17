package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import infrastructure.IAirportProvider;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AirportModel;

class AeroAirportProvider implements IAirportProvider {

    private static final String AeroApiUrl = "https://airport.api.aero/airport/match/{query}?user_key={user_key}&callback=";

    private final String userKey;

    public AeroAirportProvider(String userKey) {
        this.userKey = userKey;
    }

    @Override
    public List<AirportModel> search(String query) {
        List<AeroAirportModel> airports = new ArrayList<>();
        try {
            String url = AeroApiUrl.replace("{user_key}", this.userKey).replace("{query}", query);

            URL aeroApiUrl = new URL(url);

            HttpURLConnection con = (HttpURLConnection) aeroApiUrl.openConnection();
            con.setRequestProperty("Content-Type", "application/json;");

            int HttpResult = con.getResponseCode();

            try (InputStream stream = HttpResult < 400 ? con.getInputStream() : con.getErrorStream()) {
                if (stream == null) {
                    return new ArrayList<>();
                }

                JsonParser parser = new JsonParser();
                try (InputStreamReader is = HttpResult < 400 ? new InputStreamReader(stream, "utf-8") : new InputStreamReader(stream, "utf-8")) {
                    String response = "";

                    try (Scanner responseReader = new Scanner(is)) {
                        while (responseReader.hasNext()) {
                            response += responseReader.nextLine();
                        }
                    }

                    // We need to remove the "(" and ")" around the response;
                    response = response.substring(1, response.length() - 1);

                    JsonObject responseObject = parser.parse(response).getAsJsonObject();
                    JsonArray airportObjectArray = responseObject.get("airports").getAsJsonArray();

                    for (int i = 0; i < airportObjectArray.size(); i++) {
                        JsonObject airportObject = airportObjectArray.get(i).getAsJsonObject();

                        String code = airportObject.get("code").getAsString();
                        String name = airportObject.get("name").getAsString();
                        String city = airportObject.get("city").getAsString();
                        String country = airportObject.get("country").getAsString();
                        String timezone = airportObject.get("timezone").getAsString();
                        double latitude = airportObject.get("lat").getAsDouble();
                        double longitude = airportObject.get("lng").getAsDouble();

                        airports.add(new AeroAirportModel(code, name, city, country, timezone, latitude, longitude));
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AeroAirportProvider.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }

        List<AirportModel> results = new ArrayList<>(airports.size());
        for (AeroAirportModel aeroAirportModel : airports) {
            results.add(new AirportModel(aeroAirportModel.getCity(), aeroAirportModel.getCountry(), aeroAirportModel.getCode()));
        }

        return results;
    }

    class AeroAirportModel {

        private String code;
        private String name;
        private String city;
        private String country;
        private String timezone;
        private double lat;
        private double lng;

        public AeroAirportModel(String code, String name, String city, String country, String timezone, double lat, double lng) {
            this.code = code;
            this.name = name;
            this.city = city;
            this.country = country;
            this.timezone = timezone;
            this.lat = lat;
            this.lng = lng;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getTimezone() {
            return timezone;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

    }
}
