/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import exceptions.NoFlightFoundException;
import exceptions.NotEnoughTicketsException;
import infrastructure.IFlightService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import junit.framework.Assert;
import models.FlightModel;
import models.PassengerModel;
import models.ReservationModel;
import models.ReservatorModel;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;

public class FlightServiceTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_findAllFlights_IsNotEmpty() {
        IFlightService flightService = new FlightService(new FlightRepositorySub());

        List<FlightModel> flights = flightService.findAllFlights();

        Assert.assertFalse(flights.isEmpty());
    }

    @Test
    public void test_findAllFlights_IsNotNull() {
        IFlightService flightService = new FlightService(new FlightRepositorySub());

        List<FlightModel> flights = flightService.findAllFlights();

        Assert.assertNotNull(flights);
    }

    @Test
    public void test_findFlights_isNotNull() throws ParseException, NoFlightFoundException {
        String iataOrigin = "CPH";
        String iataDestination = "QAR";
        Date departure = new Date(116, 00, 01, 06, 00, 00);

        IFlightService flightService = new FlightService(new FlightRepositorySub());

        List<FlightModel> flights = flightService.findFlights(iataOrigin, iataDestination, departure);

        Assert.assertNotNull(flights);
    }

    @Test
    public void test_findFlights_isNotEmpty() throws ParseException, NoFlightFoundException {
        String iataOrigin = "CPH";
        String iataDestination = "QAR";
        Date departure = new Date(116, 00, 01, 06, 00, 00);

        FlightService flightService = new FlightService(new FlightRepositorySub());

        List<FlightModel> flights = flightService.findFlights(iataOrigin, iataDestination, departure);

        Assert.assertFalse(flights.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void test_findFlights_NullPointerException() throws ParseException, NoFlightFoundException {
        String iataOrigin = null;
        String iataDestination = null;
        Date departure = new Date(116, 00, 01, 06, 00, 00);

        FlightService flightService = new FlightService(new FlightRepositorySub());

        flightService.findFlights(iataOrigin, iataDestination, departure);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_findFlights_fromToNotTheSame() throws ParseException, NoFlightFoundException {
        String iataOrigin = "CPH";
        String iataDestination = "CPH";
        Date departure = new Date(116, 00, 01, 06, 00, 00);

        FlightService flightService = new FlightService(new FlightRepositorySub());

        flightService.findFlights(iataOrigin, iataDestination, departure);

    }

    @Test
    public void test_reservate_isNotNull() throws Exception {
        String flightId = "231";
        ReservatorModel reservator = new ReservatorModel("Hans", "Hansi", "Hans@Hansi.dk", "45879856");

        List<PassengerModel> passengers = new ArrayList<>();
        PassengerModel passenger1 = new PassengerModel("Rune", "Gårdsven");

        passengers.add(passenger1);

        FlightService flightService = new FlightService(new FlightRepositorySub());

        ReservationModel reservation = flightService.reservate(flightId, reservator, passengers);

        Assert.assertNotNull(reservation);
    }

    @Test(expected = NoFlightFoundException.class)
    public void test_reservate_ShouldThrowException_IfFlightNotExists() throws Exception {
        IFlightService flightService = new FlightService(new FlightRepositorySub());

        flightService.reservate("1312", new ReservatorModel("Bo", "Sørensen", "bo@sørensen.dk", "123123"), new ArrayList<PassengerModel>() {
            {
                add(new PassengerModel("Bo", "Sørensen"));
            }
        });
    }

    @Test(expected = NotEnoughTicketsException.class)
    public void test_reservate_ShouldThrowException_FlightIsFullyBooked() throws Exception {
        IFlightService flightService = new FlightService(new FlightRepositorySub());

        flightService.reservate("234", new ReservatorModel("Bo", "Sørensen", "bo@sørensen.dk", "123123"), new ArrayList<PassengerModel>() {
            {
                add(new PassengerModel("Bo", "Sørensen"));
                add(new PassengerModel("Bo2", "Sørensen2"));
                add(new PassengerModel("Bo3", "Sørensen3"));
            }
        });
    }
}
