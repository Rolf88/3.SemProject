package facades;

import converters.ReservateParser;
import infrastructure.IUserService;
import entity.UserEntity;
import infrastructure.IMomondoService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.client.Entity.json;
import models.AirlineInternalModel;
import models.PassengerModel;
import models.ReservationModel;
import rest.flyfetcher.FlyFetcher;

public class MomondoService implements IMomondoService {

    private final List<String> FLIGHT_API_URLS = new ArrayList<>();
    private final IUserService userService;

    public MomondoService(IUserService userService) {
        this.userService = userService;

        //TODO: This need to be loaded from a database
        FLIGHT_API_URLS.add("http://angularairline-plaul.rhcloud.com/");
        FLIGHT_API_URLS.add("http://timetravel-tocvfan.rhcloud.com/");
        FLIGHT_API_URLS.add("http://flightsearch-cphol24.rhcloud.com/Server/");
    }

    @Override
    public ReservationModel reservate(String baseApiUrl, String flightId, String reservatorUserId, List<PassengerModel> passengers) {
        UserEntity user = userService.getUserByUserId(reservatorUserId);

        String response = "";

        try {
            URL reservationApiUrl = new URL(baseApiUrl + "api/flightreservation");

            HttpURLConnection con = (HttpURLConnection) reservationApiUrl.openConnection();
            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                String strRequestBody = ReservateParser.serialize(flightId, user, passengers);
                
                os.write(strRequestBody.getBytes("UTF-8"));
            }

            int HttpResult = con.getResponseCode();
            try (InputStreamReader is = HttpResult < 400 ? new InputStreamReader(con.getInputStream(), "utf-8") : new InputStreamReader(con.getErrorStream(), "utf-8")) {
                try (Scanner responseReader = new Scanner(is)) {
                    while (responseReader.hasNext()) {
                        response += responseReader.nextLine();
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MomondoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(response);

        //TODO: Parse response to Reservation model
        //TODO: Save data from reservation model to our db and hook up with user
        return null;
    }

    @Override
    public List<AirlineInternalModel> findFlights(String origin, String date, int numberOfPassengers) {
        List<AirlineInternalModel> airlines = new ArrayList();

        try {
            ExecutorService pool = Executors.newFixedThreadPool(4);

            for (String url : FLIGHT_API_URLS) {
                String apiUrl = url + "api/flightinfo/" + origin + "/" + date + "/" + numberOfPassengers;

                pool.execute(new FlyFetcher(apiUrl, airlines, url));
            }

            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.DAYS);

        } catch (InterruptedException ex) {
            Logger.getLogger(MomondoService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return airlines;
    }

    @Override
    public List<AirlineInternalModel> findFlights(String origin, String destination, String date, int numberOfPassengers) {
        List<AirlineInternalModel> airlines = new ArrayList();

        try {
            ExecutorService pool = Executors.newFixedThreadPool(4);

            for (String url : FLIGHT_API_URLS) {
                String apiUrl = url + "api/flightinfo/" + origin + "/" + destination + "/" + date + "/" + numberOfPassengers;

                pool.execute(new FlyFetcher(apiUrl, airlines, url));
            }

            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.DAYS);

        } catch (InterruptedException ex) {
            Logger.getLogger(MomondoService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return airlines;
    }

}
