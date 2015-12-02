/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author RolfMoikjær
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(NotFoundException ex) {
        ErrorMessage em = new ErrorMessage(ex, 1, 404);
        return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(em)).type(MediaType.APPLICATION_JSON).build();
    }
}
