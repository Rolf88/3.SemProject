/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import converters.ReservationDeserializer;
import converters.ReservationSerializer;
import entity.FlightRepository;
import exceptions.NoFlightFoundException;
import exceptions.NotEnoughTicketsException;
import facades.EntityFactory;
import facades.FlightService;
import infrastructure.IFlightService;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import models.PassengerModel;
import models.ReservationModel;
import models.ReservatorModel;

/**
 *
 * @author RolfMoikjær
 */
@Path("flightreservation")
public class FlightReservationResource {

    @Context
    private UriInfo context;

    private Gson gson;

    public FlightReservationResource() {
        gson = new Gson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response flightReservation(String json) throws NotEnoughTicketsException, NoFlightFoundException, ParseException {
        IFlightService flightService = new FlightService(new FlightRepository(EntityFactory.getInstance().createEntityManager()));

        ReservationDeserializer resDes = new ReservationDeserializer(json);
        ReservatorModel resMod = resDes.getReservator();
        List<PassengerModel> passArr = resDes.getPassengers();
        String flightID = resDes.getFlightID();

        ReservationModel reservationModel = flightService.reservate(flightID, resMod, passArr);

        return Response.status(Response.Status.CREATED).entity(ReservationSerializer.reservationToJson(reservationModel)).build();

    }
}
