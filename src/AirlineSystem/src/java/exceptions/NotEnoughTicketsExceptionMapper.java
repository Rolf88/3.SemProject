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

/**
 *
 * @author RolfMoikjær
 */
public class NotEnoughTicketsExceptionMapper implements ExceptionMapper<NotEnoughTicketsException> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(NotEnoughTicketsException ex) {
        ErrorMessage em = new ErrorMessage(ex, 2, 400);
        return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(em)).type(MediaType.APPLICATION_JSON).build();
    }
}
