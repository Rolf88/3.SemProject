package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.EntityFactory;
import facades.MomondoService;
import facades.UserFacade;
import infrastructure.IMomondoService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.PassengerModel;
import models.ReservationModel;

@Path("booking/reservate")
public class BookingResource {

    @Context
    private UriInfo context;

    private final IMomondoService momondoService;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public BookingResource() {
        this.momondoService = new MomondoService(new UserFacade(EntityFactory.getInstance()));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{flightId}")
    public Response post(@PathParam("flightId") String flightId, String requestBody) {
        ReservationRequestBodyModel request = gson.fromJson(requestBody, ReservationRequestBodyModel.class);

        String reservatorUserId = "1"; // Den online brugers id

        List<PassengerModel> passengers = new ArrayList<>(); // Liste af fornavne og efternavne på passengerne
        for (ReservationRequestBodyModel.PassengerRequestBodyModel passenger : request.passengers) {
            passengers.add(new PassengerModel(passenger.firstname, passenger.lastname));
        }

        ReservationModel reservation = momondoService.reservate(request.baseApiUrl, flightId, reservatorUserId, passengers);

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
