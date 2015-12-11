package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.ReservationRepository;
import facades.EntityFactory;
import facades.ReservationService;
import infrastructure.IReservationService;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.ReservationModel;
import security.UserSecurityHelper;

@Path("user")
@RolesAllowed("User")
public class UserResource {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    private IReservationService reservationService;

    public UserResource() {
        EntityManager entityManager = EntityFactory.getInstance().createEntityManager();

        reservationService = new ReservationService(new ReservationRepository(entityManager));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam("Authorization") String authorization) throws ParseException {
        Long userId = UserSecurityHelper.GetUserIdFromToken(authorization);

        List<ReservationModel> reservations = reservationService.getByUserId(userId);

        return Response.ok(gson.toJson(reservations)).build();
    }
}
