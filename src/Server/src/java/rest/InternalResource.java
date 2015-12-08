/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.EntityFactory;
import facades.MomondoService;
import facades.UserFacade;
import infrastructure.IMomondoService;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.AirlineInternalModel;

/**
 * REST Web Service
 *
 * @author AlexanderSteen
 */
@Path("internal")
public class InternalResource {

    @Context
    private UriInfo context;

    private final IMomondoService momondoService;

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public InternalResource() {
        momondoService = new MomondoService(new UserFacade(EntityFactory.getInstance()));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{date}/{numTickets}")
    public Response get(@PathParam("from") String from, @PathParam("date") String dateParam, @PathParam("numTickets") String numTickets) throws InterruptedException {
        List<AirlineInternalModel> airlines = momondoService.findFlights(from, dateParam, Integer.parseInt(numTickets));

        return Response.ok(gson.toJson(airlines)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{to}/{date}/{numTickets}")
    public Response get(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String dateParam, @PathParam("numTickets") String numTickets) throws InterruptedException {
        List<AirlineInternalModel> airlines = momondoService.findFlights(from, to, dateParam, Integer.parseInt(numTickets));

        return Response.ok(gson.toJson(airlines)).build();
    }
}
