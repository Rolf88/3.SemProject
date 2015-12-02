package entity;

import infrastructure.IFlightRepository;
import java.util.Date;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
    public void test_findAllFlights_ReturnsAEmptyList_IfNoFlights() {
        List<FlightEntity> flights = this.flightRepository.findAllFlights();

        assertTrue(flights.isEmpty());
    }

    @Test
    public void test_findAllFlights_ContainsExpectedNumberOfFlights() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'ROM', 'Rom lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (1, 100, CURRENT_TIMESTAMP, 20, 1, 1, 2);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (2, 100, CURRENT_TIMESTAMP, 20, 1, 3, 2);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (3, 100, CURRENT_TIMESTAMP, 20, 1, 3, 4);"
        });

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

    @Test
    public void test_findFlights_ReturnsAEmptyListIfNoFlightsWasFound() {
        String iataOrigin = "ROK";
        String iataDestination = "NYW";
        Date departureDate = new Date();

        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, iataDestination, departureDate);

        assertTrue(flights.isEmpty());
    }

    @Test
    public void test_findFlights_ContainsExpectedNumberOfFlights() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'ROM', 'Rom lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (1, 100, '2015-12-02 10:20:24', 20, 1, 1, 4);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (2, 100, '2015-12-02 22:59:59', 20, 1, 1, 4);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (3, 100, '2015-12-02 18:20:24', 20, 1, 1, 4);"
        });

        String iataOrigin = "CPH";
        String iataDestination = "OSL";
        Date departureDate = new Date(115, 11, 2, 10, 00);
        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, iataDestination, departureDate);

        assertEquals(3, flights.size());
    }

    @Test
    public void test_findFlights_HasDepartureDate_SameDate() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'ROM', 'Rom lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (1, 100, '2015-12-02 10:20:24', 20, 1, 1, 4);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (2, 100, '2015-12-03 12:20:24', 20, 1, 1, 4);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (3, 100, '2015-12-01 09:20:24', 20, 1, 1, 4);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (4, 100, '2015-12-02 15:20:24', 20, 1, 1, 4);"
        });

        String iataOrigin = "CPH";
        String iataDestination = "OSL";
        Date departureDate = new Date(115, 11, 2, 10, 00);
        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, iataDestination, departureDate);

        assertEquals(2, flights.size());

        for (FlightEntity flight : flights) {
            assertEquals(departureDate.getDate(), flight.getDeparture().getDate());
            assertEquals(departureDate.getMonth(), flight.getDeparture().getMonth());
            assertEquals(departureDate.getYear(), flight.getDeparture().getYear());
        }
    }

}
