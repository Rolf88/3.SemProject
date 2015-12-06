/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import deploy.DeploymentConfiguration;
import entity.FlightRepository;
import exceptions.InvalidDataException;
import exceptions.NoFlightFoundException;
import exceptions.NotEnoughTicketsException;
import facades.EntityFactory;
import facades.FlightService;
import infrastructure.IFlightService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.FlightModel;

/**
 * REST Web Service
 *
 * @author AlexanderSteen
 */
@Path("flightinfo")
public class FlightinfoResource {

    @Context
    private UriInfo context;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    IFlightService fs = new FlightService(new FlightRepository(EntityFactory.getInstance().createEntityManager()));

    public FlightinfoResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{date}/{numTickets}")
    public Response get(@PathParam("from") String from, @PathParam("date") String dateParam, @PathParam("numTickets") String numTickets) throws InvalidDataException, ParseException, NoFlightFoundException, NotEnoughTicketsException {
        Date date;
        int tickets;

        if (!(from.length() == 2 || from.length() == 3)) {
            throw new InvalidDataException("Invalid from");
        }
        try {
            DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            date = sdfISO.parse(dateParam);
        } catch (ParseException e) {
            throw new InvalidDataException("Invalid date");
        }

        try {
            tickets = Integer.parseInt(numTickets);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Invalid ticket");
        }

        IFlightService fs = new FlightService(new FlightRepository(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME).createEntityManager()));
        List<FlightModel> fm = fs.findFlights(from, date, tickets);

        JsonObject jo = new JsonObject();
        jo.addProperty("airline", "Gruppe 42");
        jo.add("flights", gson.toJsonTree(fm));

        return Response.ok(gson.toJson(jo)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{to}/{date}/{numTickets}")
    public Response get(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String dateParam, @PathParam("numTickets") String numTickets) throws InvalidDataException, ParseException, NoFlightFoundException, NotEnoughTicketsException {
        Date date;
        int tickets;

        if (!(from.length() == 2 || from.length() == 3)) {
            throw new InvalidDataException("Invalid from");
        }
        if (!(to.length() == 2 || to.length() == 3)) {
            throw new InvalidDataException("Invalid to");
        }
        
        try {
            DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            date = sdfISO.parse(dateParam);
        } catch (ParseException e) {
            throw new InvalidDataException("Invalid date");
        }

        try {
            tickets = Integer.parseInt(numTickets);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Invalid ticket");
        }

        IFlightService fs = new FlightService(new FlightRepository(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME).createEntityManager()));
        List<FlightModel> fm = fs.findFlights(from, to, date, tickets);

        JsonObject jo = new JsonObject();
        jo.addProperty("airline", "Gruppe 42");
        jo.add("flights", gson.toJsonTree(fm));

        return Response.ok(gson.toJson(jo)).build();
    }
}
