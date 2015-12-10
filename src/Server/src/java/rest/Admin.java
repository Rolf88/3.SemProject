package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import facades.AdminFacade;
import facades.EntityFactory;
import infrastructure.IAdminService;
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
import security.Secrets;
import security.UserSecurityHelper;

@Path("admin")
@RolesAllowed("Admin")
public class Admin {

    private IAdminService service;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create();
    
    public Admin() {
        service = new AdminFacade(EntityFactory.getInstance());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam("Authorization") String authorization) throws ParseException, JOSEException {
        Long userId = UserSecurityHelper.GetUserIdFromToken(authorization);
        
        List<ReservationModel> reservations = service.getReservations();
        
        return Response.ok(gson.toJson(reservations)).build();
    }
}
