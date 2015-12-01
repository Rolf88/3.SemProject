package facades;

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
}
