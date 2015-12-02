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
public class GeneralErrorExceptionMapper implements ExceptionMapper<Exception> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(Exception ex) {
        ErrorMessage em = new ErrorMessage(ex, 4, 500);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(em)).type(MediaType.APPLICATION_JSON).build();
    }
}
