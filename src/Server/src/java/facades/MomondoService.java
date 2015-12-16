package facades;

import entity.FlightApiUrls;
import infrastructure.IUserService;
import entity.UserEntity;
import infrastructure.IAirportProvider;
import infrastructure.IMomondoService;
import infrastructure.IReservationService;
import infrastructure.ISearchRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AirlineInternalModel;
import models.AirportModel;
import models.HighscoreModel;
import models.PassengerModel;
import models.ReservateModel;
import rest.flyfetcher.FlyFetcher;

public class MomondoService implements IMomondoService {

    private final List<String> FLIGHT_API_URLS = new ArrayList<>();
    private final IUserService userService;
    private final IReservationService reservationService;
    private final IAirportProvider airportProvider;
    private final ISearchRepository searchRepository;
    private FlightApiUrlsFacade facade;
    private List<FlightApiUrls> flightUrlsList = new ArrayList<>();

    public MomondoService(IUserService userService, IReservationService reservationService, ISearchRepository searchRepository) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.airportProvider = new AeroAirportProvider("cfbdd6018b8a2e369bd541cc68950bad");
        this.searchRepository = searchRepository;
        this.facade = new FlightApiUrlsFacade(EntityFactory.getInstance().createEntityManager());

        //TODO: This need to be loaded from a database
        flightUrlsList = facade.getFlightApiUrls();

        for (int i = 0; i < flightUrlsList.size(); i++) {
            FLIGHT_API_URLS.add(flightUrlsList.get(i).getUrl());
        }

//        FLIGHT_API_URLS.add("http://angularairline-plaul.rhcloud.com/");
//        FLIGHT_API_URLS.add("http://timetravel-tocvfan.rhcloud.com/");
//        FLIGHT_API_URLS.add("http://flightairline-cphol24.rhcloud.com/AirlineSystem/");
    }

    @Override
    public ReservateModel reservate(String baseApiUrl, String flightId, String reservatorUserId, List<PassengerModel> passengers) {
        UserEntity user = userService.getUserByUserId(reservatorUserId);

        return this.reservationService.reservate(baseApiUrl, flightId, user, passengers);
    }

    @Override
    public List<AirlineInternalModel> findFlights(String origin, String departureDate, int numberOfPassengers) {
        return findFlights(origin, null, departureDate, numberOfPassengers);
    }

    @Override
    public List<AirlineInternalModel> findFlights(String origin, String destination, String departureDate, int numberOfPassengers) {
        List<AirlineInternalModel> airlines = new ArrayList();

        try {
            ExecutorService pool = Executors.newFixedThreadPool(4);

            String apiEndpointPath = (destination == null)
                    ? "api/flightinfo/" + origin + "/" + departureDate + "/" + numberOfPassengers
                    : "api/flightinfo/" + origin + "/" + destination + "/" + departureDate + "/" + numberOfPassengers;

            for (String url : FLIGHT_API_URLS) {
                String apiUrl = url + apiEndpointPath;

                pool.execute(new FlyFetcher(apiUrl, airlines, url));
            }

            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.DAYS);

        } catch (InterruptedException ex) {
            Logger.getLogger(MomondoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Log the search
        try {
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            this.searchRepository.add(origin, destination, dateFormatter.parse(departureDate), numberOfPassengers, new Date());
        } catch (ParseException ex) {
            Logger.getLogger(MomondoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return airlines;
    }

    @Override
    public List<AirportModel> searchAirports(String query) {
        return this.airportProvider.search(query);
    }

    @Override
    public List<HighscoreModel<String>> getDestinationHighscore(int size) {
        if (size <= 0) {
            return null;
        }

        return this.searchRepository.getTopDestination(size);
    }

}
