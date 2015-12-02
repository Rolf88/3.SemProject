package entity;

import infrastructure.IFlightRepository;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import test.PersistenceHelper;

public class FlightRepositoryTest {

    private IFlightRepository flightRepository;

    @Before
    public void setUp() {
        flightRepository = new FlightRepository(PersistenceHelper.getEntityManager());
    }

    @After
    public void teardown() {
        PersistenceHelper.resetDatabase();
    }

    @Test
    public void test_findAllFlights_IsNotNull() {
        List<FlightEntity> flights = this.flightRepository.findAllFlights();

        assertNotNull(flights);
    }

    @Test
    public void test_findAllFlights_ContainsExpectedNumberOfFlights() {
        List<FlightEntity> flights = this.flightRepository.findAllFlights();

        assertEquals(3, flights.size());
    }

    @Test
    public void test_findAllFlights_DoesNothaveNullRelations() {
        List<FlightEntity> flights = this.flightRepository.findAllFlights();

        for (FlightEntity flight : flights) {
            assertNotNull(flight.getAirline());
            assertNotNull(flight.getOrigin());
            assertNotNull(flight.getDestination());
            assertNotNull(flight.getReservations());
        }
    }
}
