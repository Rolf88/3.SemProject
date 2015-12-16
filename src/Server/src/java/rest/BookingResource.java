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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.PassengerModel;
import models.ReservateModel;
import security.UserSecurityHelper;

@Path("booking/reservate")
public class BookingResource {

    @Context
    private UriInfo context;

    private final IMomondoService momondoService;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public BookingResource() {
        EntityManager entityManager = EntityFactory.getInstance().createEntityManager();

        this.momondoService = new MomondoService(new UserFacade(entityManager), new ReservationService(new ReservationRepository(entityManager)), new SearchRepository(entityManager));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{flightId}")
    @RolesAllowed("User")
    public Response post(@HeaderParam("Authorization") String authorization, @PathParam("flightId") String flightId, String requestBody) throws ParseException {
        ReservationRequestBodyModel request = gson.fromJson(requestBody, ReservationRequestBodyModel.class);

        String reservatorUserId = UserSecurityHelper.GetUserIdFromToken(authorization).toString(); // Den online brugers id

        List<PassengerModel> passengers = new ArrayList<>(); // Liste af fornavne og efternavne på passengerne
        for (ReservationRequestBodyModel.PassengerRequestBodyModel passenger : request.passengers) {
            passengers.add(new PassengerModel(passenger.firstname, passenger.lastname));
        }

        ReservateModel reservation = momondoService.reservate(request.baseApiUrl, flightId, reservatorUserId, passengers);

        if (reservation == null) {
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }

        return Response.ok(gson.toJson(reservation)).build();
    }

    private class ReservationRequestBodyModel {

        private String baseApiUrl;

        private List<PassengerRequestBodyModel> passengers;

        private class PassengerRequestBodyModel {

            private String firstname;

            private String lastname;
        }
    }
}
