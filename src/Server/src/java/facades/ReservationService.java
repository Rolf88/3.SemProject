package facades;

import converters.ReservateParser;
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
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.PassengerModel;
import models.ReservationModel;

public class ReservationService implements IReservationService {

    private final IReservationRepository reservationRepository;

    public ReservationService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationModel reservate(String baseApiUrl, String flightId, UserEntity reservatorUser, List<PassengerModel> passengers) {
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
            ReservationModel reservation = ReservateParser.deserialize(response);

            // Save the reservation to the database
            reservationRepository.add(reservatorUser.getId(), reservation);

            return reservation;
        } catch (IOException | ParseException ex) {
            Logger.getLogger(MomondoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
