package facades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import junit.framework.Assert;
import models.FlightModel;
import models.PassengerModel;
import models.ReservationModel;
import models.ReservatorModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FlightServiceTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_findAllFlights_IsNotEmpty() {
        FlightService flightService = new FlightService();

        List<FlightModel> flights = flightService.findAllFlights();

        Assert.assertFalse(flights.isEmpty());
    }

    @Test
    public void test_findAllFlights_IsNotNull() {
        FlightService flightService = new FlightService();

        List<FlightModel> flights = flightService.findAllFlights();

        Assert.assertNotNull(flights);
    }

    @Test
    public void test_findFlights_isNotNull() throws ParseException {
        String iataOrigin = "CBH";
        String iataDestination = "QAR";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date departure = sdf1.parse("01-01-2016 06:00:00");

        FlightService flightService = new FlightService();

        List<FlightModel> flights = flightService.findFlights(iataOrigin, iataDestination, departure);

        Assert.assertNotNull(flights);
    }

    @Test
    public void test_findFlights_isNotEmpty() throws ParseException {
        String iataOrigin = "CBH";
        String iataDestination = "QAR";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date departure = sdf1.parse("01-01-2016 06:00:00");

        FlightService flightService = new FlightService();

        List<FlightModel> flights = flightService.findFlights(iataOrigin, iataDestination, departure);

        Assert.assertFalse(flights.isEmpty());
    }

    @Test
    public void test_reservate_isNotNull() {
        int flightId = 546;
        ReservatorModel reservator = new ReservatorModel("Hans", "Hansi", "Hans@Hansi.dk", "45879856");

        List<PassengerModel> passengers = new ArrayList<>();
        PassengerModel passenger1 = new PassengerModel("Rune", "Gårdsven");

        passengers.add(passenger1);

        FlightService flightService = new FlightService();

        ReservationModel reservation = flightService.reservate(flightId, reservator, passengers);

        Assert.assertNotNull(reservation);
    }

    @Test(expected = NullPointerException.class)
    public void test_findFlights_NullPointerException() throws ParseException {
        String iataOrigin = null;
        String iataDestination = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date departure = sdf1.parse("01-01-2016 06:00:00");

        FlightService flightService = new FlightService();

        List<FlightModel> flights = flightService.findFlights(iataOrigin, iataDestination, departure);

    }

    @Test(expected = NullPointerException.class)
    public void test_reservate_NullPointerException() {
        int flightId = 546;
        ReservatorModel reservator = new ReservatorModel(null, null, null, null);

        List<PassengerModel> passengers = new ArrayList<>();

        FlightService flightService = new FlightService();

        ReservationModel reservation = flightService.reservate(flightId, reservator, passengers);
    }
}
