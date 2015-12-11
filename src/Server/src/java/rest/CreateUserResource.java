/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import converters.UserSerializer;
import entity.UserEntity;
import exceptions.DataAllreadyExistException;
import exceptions.InvalidDataException;
import facades.EntityFactory;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author RolfMoikjær
 */
@Path("createuser")
public class CreateUserResource {

    @Context
    private UriInfo context;

    private Gson gson;
    private JsonParser jsonParser;

    public CreateUserResource() {
        gson = new Gson();
        jsonParser = new JsonParser();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json) throws InvalidDataException, DataAllreadyExistException, NoSuchAlgorithmException, InvalidKeySpecException {
        UserFacade facade = new UserFacade(EntityFactory.getInstance().createEntityManager());
        UserEntity user = gson.fromJson(json, UserEntity.class);

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getPhone().isEmpty()) {
            throw new InvalidDataException("Email, Password and phone number are needed");
        }

        user = facade.createUser(user);

        return Response.status(Response.Status.CREATED).entity(UserSerializer.userToJson(user)).build();
    }
}
