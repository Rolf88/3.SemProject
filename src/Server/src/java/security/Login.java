package security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import entity.UserEntity;
import facades.EntityFactory;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class Login {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String gt() {
        return "{\"txt\" : \"TEST\"}";

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String jsonString) throws JOSEException, NoSuchAlgorithmException, InvalidKeySpecException {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String email = json.get("email").getAsString();
        String password = json.get("password").getAsString();
        JsonObject responseJson = new JsonObject();
        List<String> roles;

        if ((roles = authenticate(email, password)) != null) {
            UserFacade facade = new UserFacade(EntityFactory.getInstance().createEntityManager());
            UserEntity user = facade.getUserByEmail(email);
            Long userId = user.getId();
            String token = createToken(userId, email, roles);
            responseJson.addProperty("userId", userId);
            responseJson.addProperty("token", token);
            return Response.ok(new Gson().toJson(responseJson)).build();
        }
        throw new NotAuthorizedException("Ilegal username or password", Response.Status.UNAUTHORIZED);
    }

    private List<String> authenticate(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserFacade facade = new UserFacade(EntityFactory.getInstance().createEntityManager());
        return facade.authenticateUser(email, password);
    }

    private String createToken(Long userId, String email, List<String> roles) throws JOSEException {
        StringBuilder res = new StringBuilder();
        for (String string : roles) {
            res.append(string);
            res.append(",");
        }
        String rolesAsString = res.length() > 0 ? res.substring(0, res.length() - 1) : "";

        String issuer = "semester3demo-cphbusiness.dk-computerScience";
        JWSSigner signer = new MACSigner(Secrets.ADMIN.getBytes());

        JWTClaimsSet claimsSet = new JWTClaimsSet();
        claimsSet.setSubject(userId.toString());
        claimsSet.setClaim("userId", userId);
        claimsSet.setClaim("email", email);
        claimsSet.setCustomClaim("roles", rolesAsString);
        Date date = new Date();
        claimsSet.setIssueTime(date);
        claimsSet.setExpirationTime(new Date(date.getTime() + 1000 * 60 * 60));
        claimsSet.setIssuer(issuer);

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();

    }
}
