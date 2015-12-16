package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.ReservationRepository;
import entity.SearchRepository;
import facades.EntityFactory;
import facades.MomondoService;
import facades.ReservationService;
import facades.UserFacade;
import infrastructure.IMomondoService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import models.AirportModel;

@Path("airports")
public class AirportResource {

    @Context
    private UriInfo context;

    private IMomondoService momondoService;

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public AirportResource() {
        EntityManager entityManager = EntityFactory.getInstance().createEntityManager();

        this.momondoService = new MomondoService(new UserFacade(entityManager), new ReservationService(new ReservationRepository(entityManager)), new SearchRepository(entityManager));
    }

    @GET
    @Path("search/{query}")
    @Produces("application/json")
    public Response search(@PathParam("query") String query) {
        List<AirportModel> airports = momondoService.searchAirports(query);

        return Response.ok(gson.toJson(airports)).build();
    }
}
