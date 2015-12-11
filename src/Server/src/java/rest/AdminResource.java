package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.JOSEException;
import entity.ReservationRepository;
import facades.EntityFactory;
import facades.ReservationService;
import infrastructure.IReservationService;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.ReservationModel;

@Path("admin")
@RolesAllowed("Admin")
public class AdminResource {

    private IReservationService reservationService;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();

    public AdminResource() {
        reservationService = new ReservationService(new ReservationRepository(EntityFactory.getInstance().createEntityManager()));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() throws ParseException, JOSEException {
        List<ReservationModel> reservations = reservationService.findAll();

        return Response.ok(gson.toJson(reservations)).build();
    }
}
