/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import exceptions.InvalidDataException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author AlexanderSteen
 */
@Path("flightinfo")
public class FlightinfoResource {

    @Context
    private UriInfo context;

    public FlightinfoResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{date}/{numTickets}")
    public Response get(@PathParam("from") String from, @PathParam("date") String dateParam, @PathParam("numTickets") String numTickets) throws InvalidDataException, ParseException {
        Date date;
        int tickets;

        if(!(from.length() == 2 || from.length() == 3)){
            throw new InvalidDataException("Invalid from");
        }
        try{
            DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"); 
            date = sdfISO.parse(dateParam);
        }catch(ParseException e){
            throw new InvalidDataException("Invalid date");
        }
        
        try{
            tickets = Integer.parseInt(numTickets);
        }catch(NumberFormatException e){
            throw new InvalidDataException("Invalid ticket");
        }
        
        
        
        
        return Response.ok().build();
    }
}
