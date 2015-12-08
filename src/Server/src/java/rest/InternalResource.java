/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.AirlineInternalModel;
import models.AirlineModel;
import rest.flyfetcher.FlyFetcher;

/**
 * REST Web Service
 *
 * @author AlexanderSteen
 */
@Path("internal")
public class InternalResource {

    @Context
    private UriInfo context;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    List<String> urls = new ArrayList();

    public InternalResource() {
        urls.add("http://angularairline-plaul.rhcloud.com/");
        urls.add("http://timetravel-tocvfan.rhcloud.com/");
        urls.add("http://flightsearch-cphol24.rhcloud.com/Server/");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{date}/{numTickets}")
    public Response get(@PathParam("from") String from, @PathParam("date") String dateParam, @PathParam("numTickets") String numTickets) throws InterruptedException{
        List<AirlineInternalModel> airlines = new ArrayList();

        ExecutorService pool = Executors.newFixedThreadPool(4);
        
        for (String url : urls) {
            String apiUrl = url + "api/flightinfo/" + from + "/" + dateParam + "/" + numTickets;
                    
            pool.execute(new FlyFetcher(apiUrl, airlines, url));
        }
        
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        
        return Response.ok(gson.toJson(airlines)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{to}/{date}/{numTickets}")
    public Response get(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String dateParam, @PathParam("numTickets") String numTickets) throws InterruptedException{
        List<AirlineInternalModel> airlines = new ArrayList();

        ExecutorService pool = Executors.newFixedThreadPool(4);
        
        for (String url : urls) {
            String apiUrl = url + "api/flightinfo/" + from + "/" + to + "/" + dateParam + "/" + numTickets;
                    
            pool.execute(new FlyFetcher(apiUrl, airlines, url));
        }
        
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
        
        return Response.ok(gson.toJson(airlines)).build();
    }
   
}
