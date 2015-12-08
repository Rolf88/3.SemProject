package facades;

import infrastructure.IMomondoService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AirlineInternalModel;
import models.ReservationModel;
import rest.flyfetcher.FlyFetcher;

public class MomondoService implements IMomondoService {

    private List<String> FLIGHT_API_URLS = new ArrayList<>();

    public MomondoService() {
        FLIGHT_API_URLS.add("http://angularairline-plaul.rhcloud.com/");
        FLIGHT_API_URLS.add("http://timetravel-tocvfan.rhcloud.com/");
        FLIGHT_API_URLS.add("http://flightsearch-cphol24.rhcloud.com/Server/");
    }

    @Override
    public ReservationModel reservate(String flightId, String userId) {
        // TODO: Add reservation
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
            Logger.getLogger(MomondoService.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MomondoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return airlines;
    }

}
