package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.EntityFactory;
import facades.UserFacade;
import infrastructure.IUserService;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
    private IUserService service;

    public UserResource() {
        service = new UserFacade(EntityFactory.getInstance());
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam("Authorization") String authorization) throws ParseException {
        Long userId = UserSecurityHelper.GetUserIdFromToken(authorization);
        List<ReservationModel> reservations = service.getReservations(userId);
        return Response.ok(gson.toJson(reservations)).build();
    }
}
