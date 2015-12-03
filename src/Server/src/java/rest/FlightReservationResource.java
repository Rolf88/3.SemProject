/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.PassengerEntity;
import facades.FlightService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response flightReservation(String json) {
//        FlightService flightService = new FlightService();
//        
//    }
}
