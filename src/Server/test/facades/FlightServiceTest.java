package facades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import junit.framework.Assert;
import models.FlightModel;
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
}
