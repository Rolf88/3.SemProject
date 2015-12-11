package facades;

import converters.ReservateParser;
import entity.PassengerEntity;
import entity.ReservationEntity;
import entity.UserEntity;
import static entity.deploy.ReservationEntity_.user;
import infrastructure.IReservationRepository;
import infrastructure.IReservationService;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import models.FlightModel;
import models.PassengerModel;
import models.ReservateModel;
import models.ReservationModel;
import models.ReservatorModel;

public class ReservationService implements IReservationService {

    private final IReservationRepository reservationRepository;

    public ReservationService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservateModel reservate(String baseApiUrl, String flightId, UserEntity reservatorUser, List<PassengerModel> passengers) {
        String response = "";

        try {
            URL reservationApiUrl = new URL(baseApiUrl + "api/flightreservation");

            HttpURLConnection con = (HttpURLConnection) reservationApiUrl.openConnection();
            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                String strRequestBody = ReservateParser.serialize(flightId, reservatorUser, passengers);

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

            // Deserialize the response from the api
            ReservateModel reservation = ReservateParser.deserialize(response);

            // Save the reservation to the database
            reservationRepository.add(reservatorUser.getId(), reservation);

            return reservation;
        } catch (IOException | ParseException ex) {
            Logger.getLogger(MomondoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public List<ReservationModel> getByUserId(Long userId) {
        List<ReservationEntity> reservations = this.reservationRepository.getByUserId(userId);
        
        return MapReservations(reservations);
    }
    
    @Override
    public List<ReservationModel> findAll(){
        List<ReservationEntity> reservations = this.reservationRepository.findAll();
        
        return MapReservations(reservations);
    }

    private List<ReservationModel> MapReservations(List<ReservationEntity> reservationEntities) {
        List<ReservationModel> reservations = new ArrayList<>(reservationEntities.size());

        for (ReservationEntity reservationEntity : reservationEntities) {
            FlightModel flight = new FlightModel(reservationEntity.getFlightId(), reservationEntity.getDepartureDate(), -1, reservationEntity.getFlightTime(), reservationEntity.getDestination(), reservationEntity.getOrigin());

            List<PassengerModel> passengers = new ArrayList<>(reservationEntity.getPasssengers().size());

            for (PassengerEntity passengerEntity : reservationEntity.getPasssengers()) {
                passengers.add(new PassengerModel(passengerEntity.getFirstname(), passengerEntity.getLastname()));
            }

            ReservatorModel reservator = new ReservatorModel(reservationEntity.getUser().getFirstname(), reservationEntity.getUser().getLastname(), reservationEntity.getUser().getEmail(), reservationEntity.getUser().getPhone());

            reservations.add(new ReservationModel(reservator, flight, passengers));
        }
        
        return reservations;
    }

}
