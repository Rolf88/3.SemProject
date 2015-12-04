package entity;

import infrastructure.IFlightRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.PassengerModel;
import models.ReservatorModel;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (1, 100, CURRENT_TIMESTAMP, 20, 1, 1, 2, 'sdfsdf');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (2, 100, CURRENT_TIMESTAMP, 20, 1, 3, 2, 'sdfsdf3s');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (3, 100, CURRENT_TIMESTAMP, 20, 1, 3, 4, 'sdfsdf2');"
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
    public void test_findFlights_WithoutDestination_ReturnsAEmptyListIfNoFlightsWasFound() {
        String iataOrigin = "ROK";
        Date departureDate = new Date();

        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, departureDate, 1);

        assertTrue(flights.isEmpty());
    }

    @Test
    public void test_findFlights_WithoutDestination_ContainsExpectedNumberOfFlights() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'ROM', 'Rom lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (1, 100, '2015-12-02 10:20:24', 20, 1, 1, 4, 'sadagf');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (2, 100, '2015-12-02 22:59:59', 20, 1, 1, 3, 'sadagf3');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (3, 100, '2015-12-02 18:20:24', 20, 1, 1, 2, 'sadagf2');"
        });

        String iataOrigin = "CPH";
        Date departureDate = new Date(115, 11, 2, 10, 00);
        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, departureDate, 1);

        assertEquals(3, flights.size());
    }

    @Test
    public void test_findFlights_WithoutDestination_HasDepartureDate_SameDate() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'ROM', 'Rom lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (1, 100, '2015-12-02 10:20:24', 20, 1, 1, 4, 'sadagf');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (2, 100, '2015-12-03 12:20:24', 20, 1, 1, 2, 'sadagf5');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (3, 100, '2015-12-01 09:20:24', 20, 1, 1, 3, 'sadagf3');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (4, 100, '2015-12-02 15:20:24', 20, 1, 1, 2, 'sadagf2');"
        });

        String iataOrigin = "CPH";
        Date departureDate = new Date(115, 11, 2, 10, 00);
        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, departureDate, 1);

        assertEquals(2, flights.size());

        for (FlightEntity flight : flights) {
            assertEquals(departureDate.getDate(), flight.getDeparture().getDate());
            assertEquals(departureDate.getMonth(), flight.getDeparture().getMonth());
            assertEquals(departureDate.getYear(), flight.getDeparture().getYear());
        }
    }

    @Test
    public void test_findFlights_WithDestination_ReturnsAEmptyListIfNoFlightsWasFound() {
        String iataOrigin = "ROK";
        String iataDestination = "NYW";
        Date departureDate = new Date();

        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, iataDestination, departureDate);

        assertTrue(flights.isEmpty());
    }

    @Test
    public void test_findFlights_WithDestination_ContainsExpectedNumberOfFlights() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'ROM', 'Rom lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (1, 100, '2015-12-02 10:20:24', 20, 1, 1, 4, 'tsfdf');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (2, 100, '2015-12-02 22:59:59', 20, 1, 1, 4, 'tsfdf2');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (3, 100, '2015-12-02 18:20:24', 20, 1, 1, 4, 'tsfdf3');"
        });

        String iataOrigin = "CPH";
        String iataDestination = "OSL";
        Date departureDate = new Date(115, 11, 2, 10, 00);
        List<FlightEntity> flights = this.flightRepository.findFlights(iataOrigin, iataDestination, departureDate);

        assertEquals(3, flights.size());
    }

    @Test
    public void test_findFlights_WithDestination_HasDepartureDate_SameDate() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'ROM', 'Rom lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (1, 100, '2015-12-02 10:20:24', 20, 1, 1, 4, 'tsfdf2');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (2, 100, '2015-12-03 12:20:24', 20, 1, 1, 4, 'tsfdfdsa');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (3, 100, '2015-12-01 09:20:24', 20, 1, 1, 4, 'tsfdf4');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (4, 100, '2015-12-02 15:20:24', 20, 1, 1, 4, 'tsfdf');"
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

    @Test
    public void test_createReservation_ShouldNotReturnNull() {
        setupReservationSchema();

        int flightId = 1;
        ReservatorModel reservator = new ReservatorModel("Kim", "Madsen", "kim@madsen.dk", "2131245");
        List<PassengerModel> passengers = new ArrayList<>();
        passengers.add(new PassengerModel("Kim", "Madsen"));

        FlightEntity flightEntity = this.flightRepository.getFlightById(1);

        ReservationEntity reservation = this.flightRepository.createReservation(flightEntity, reservator, passengers);

        assertNotNull(reservation);
    }

    @Test
    public void test_createReservation_ShouldReturnMappedReservationEntity() {
        setupReservationSchema();

        long flightId = 1;
        ReservatorModel reservator = new ReservatorModel("Kim", "Madsen", "kim@madsen.dk", "2131245");
        List<PassengerModel> passengers = new ArrayList<>();

        PassengerModel passenger1 = new PassengerModel("Kim", "Madsen");
        passengers.add(passenger1);

        PassengerModel passenger2 = new PassengerModel("Line", "Madsen");
        passengers.add(passenger2);

        FlightEntity flightEntity = this.flightRepository.getFlightById(1);

        ReservationEntity reservation = this.flightRepository.createReservation(flightEntity, reservator, passengers);

        assertNotNull(reservation.getFlight());
        assertEquals((long) reservation.getFlight().getId(), flightId);
        assertEquals(reservator.getFirstname(), reservation.getFirstname());
        assertEquals(reservator.getLastname(), reservation.getLastname());
        assertEquals(reservator.getEmail(), reservation.getEmail());
        assertEquals(reservator.getPhone(), reservation.getPhone());

        PassengerEntity passengerEntity1 = reservation.getPasssengers().get(0);
        assertEquals(passenger1.getFirstname(), passengerEntity1.getFirstname());
        assertEquals(passenger1.getLastname(), passengerEntity1.getLastname());

        PassengerEntity passengerEntity2 = reservation.getPasssengers().get(1);
        assertEquals(passenger2.getFirstname(), passengerEntity2.getFirstname());
        assertEquals(passenger2.getLastname(), passengerEntity2.getLastname());
    }

    @Test
    public void test_createReservation_ShouldCreateAReservation() {
        setupReservationSchema();

        int flightId = 1;
        ReservatorModel reservator = new ReservatorModel("Kim", "Madsen", "kim@madsen.dk", "2131245");
        List<PassengerModel> passengers = new ArrayList<>();
        passengers.add(new PassengerModel("Kim", "Madsen"));

        FlightEntity flightEntity = this.flightRepository.getFlightById(1);

        long before = PersistenceHelper.count("SELECT COUNT(1) FROM Reservation");
        this.flightRepository.createReservation(flightEntity, reservator, passengers);
        long after = PersistenceHelper.count("SELECT COUNT(1) FROM Reservation");

        assertEquals(before + 1, after);
    }

    @Test
    public void test_getFlightById_ShouldReturnFlight_IfFound() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (10, 'VIL', 'Vilstrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (24, 'ALS', 'Als lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (13, 100, '2015-12-02 10:20:24', 20, 1, 24, 10, '123123');"
        });

        int flightId = 13;
        FlightEntity flight = this.flightRepository.getFlightById(flightId);

        assertNotNull(flight);
        assertEquals(100, flight.getCapacity());

        assertNotNull(flight.getAirline());
        assertEquals("42 Airlines", flight.getAirline().getName());

        assertNotNull(flight.getOrigin());
        assertEquals("VIL", flight.getOrigin().getIataCode());

        assertNotNull(flight.getDestination());
        assertEquals("ALS", flight.getDestination().getIataCode());

        assertEquals((int) 20, (int) flight.getPrice());
    }


    @Test
    public void test_getFlightById_ByFlightId_ShouldReturnFlight_IfFound() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (10, 'VIL', 'Vilstrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (24, 'ALS', 'Als lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (13, 100, '2015-12-02 10:20:24', 20, 1, 24, 10, '123123');"
        });

        String flightId = "123123";
        FlightEntity flight = this.flightRepository.getFlightById(flightId);

        assertNotNull(flight);
        assertEquals(100, flight.getCapacity());

        assertNotNull(flight.getAirline());
        assertEquals("42 Airlines", flight.getAirline().getName());

        assertNotNull(flight.getOrigin());
        assertEquals("VIL", flight.getOrigin().getIataCode());

        assertNotNull(flight.getDestination());
        assertEquals("ALS", flight.getDestination().getIataCode());

        assertEquals((int) 20, (int) flight.getPrice());
    }

    @Test
    public void test_getFlightById_ShouldReturnNull_IfNotFound() {
        int flightId = 1323423;
        FlightEntity flight = this.flightRepository.getFlightById(flightId);

        assertNull(flight);
    }

    @Test
    public void test_getNumberOfPassengers_ShouldReturnCorrectNumberOfPassengers() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (1, 100, '2015-12-02 10:20:24', 20, 1, 1, 2, 'fsdfsdf');",
            "INSERT INTO reservation (id, firstname, lastname, email, phone, flight_id) VALUES (1, 'Oliver', 'Madsen', 'oliver@madsen.dk', '1231232', 1);",
            "INSERT INTO passenger (id, firstname, lastname, reservation_id) VALUES (1, 'Oliver', 'Madsen', 1);",
            "INSERT INTO passenger (id, firstname, lastname, reservation_id) VALUES (2, 'Rolf', 'Madsen', 1);",
            "INSERT INTO reservation_passenger (reservation_id, passsengers_id) VALUES (1, 1);",
            "INSERT INTO reservation_passenger (reservation_id, passsengers_id) VALUES (1, 2);",
            "INSERT INTO reservation (id, firstname, lastname, email, phone, flight_id) VALUES (2, 'Lukas', 'Madsen', 'oliver@madsen.dk', '1231232', 1);",
            "INSERT INTO passenger (id, firstname, lastname, reservation_id) VALUES (3, 'Lukas', 'Madsen', 2);",
            "INSERT INTO passenger (id, firstname, lastname, reservation_id) VALUES (4, 'Alexander', 'Madsen', 2);",
            "INSERT INTO reservation_passenger (reservation_id, passsengers_id) VALUES (2, 3);",
            "INSERT INTO reservation_passenger (reservation_id, passsengers_id) VALUES (2, 4);"
        });

        int numberOfPassengers = this.flightRepository.getNumberOfPassengers(1);
        
        assertEquals(4, numberOfPassengers);
    }

    private void setupReservationSchema() {
        PersistenceHelper.execute(new String[]{
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id, flightId) VALUES (1, 100, '2015-12-02 10:20:24', 20, 1, 1, 4, 'sdasdag');"
        });
    }
}
