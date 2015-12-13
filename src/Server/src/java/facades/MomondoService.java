package facades;

import infrastructure.IUserService;
import entity.UserEntity;
import infrastructure.IMomondoService;
import infrastructure.IReservationService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AirlineInternalModel;
import models.PassengerModel;
import models.ReservateModel;
import rest.flyfetcher.FlyFetcher;

public class MomondoService implements IMomondoService {

    private final List<String> FLIGHT_API_URLS = new ArrayList<>();
    private final IUserService userService;
    private final IReservationService reservationService;

    public MomondoService(IUserService userService, IReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;

        //TODO: This need to be loaded from a database
        FLIGHT_API_URLS.add("http://angularairline-plaul.rhcloud.com/");
        FLIGHT_API_URLS.add("http://timetravel-tocvfan.rhcloud.com/");
        FLIGHT_API_URLS.add("http://flightairline-cphol24.rhcloud.com/AirlineSystem/");
    }

    @Override
    public ReservateModel reservate(String baseApiUrl, String flightId, String reservatorUserId, List<PassengerModel> passengers) {
        UserEntity user = userService.getUserByUserId(reservatorUserId);

        return this.reservationService.reservate(baseApiUrl, flightId, user, passengers);
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
