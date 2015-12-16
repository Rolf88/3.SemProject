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
import javax.ws.rs.core.Response;
import models.HighscoreModel;

@Path("statistics")
public class StatisticsResource {

    @Context
    private UriInfo context;

    private final IMomondoService momondoService;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public StatisticsResource() {
        EntityManager entityManager = EntityFactory.getInstance().createEntityManager();

        this.momondoService = new MomondoService(new UserFacade(entityManager), new ReservationService(new ReservationRepository(entityManager)), new SearchRepository(entityManager));
    }

    @GET
    @Path("highscores/destinations")
    @Produces("application/json")
    public Response destinationHighscore() {
        List<HighscoreModel<String>> highscore = this.momondoService.getDestinationHighscore(5);

        return Response.ok(gson.toJson(highscore)).build();
    }
}
