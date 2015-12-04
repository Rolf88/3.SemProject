/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author priva_000
 */
public class ReservationDeserializerTest {
    
    public ReservationDeserializerTest() {
    }

    @Test
    public void testGetReservator() {
       ReservationDeserializer deserializer = new ReservationDeserializer("{\"flightID\":\"COL3257x100x2016-01-15T00:30:00.000Z\",\"numberOfSeats\":2,\"ReserveeName\":\"dasfadlg\",\"ReservePhone\":\"oisdjfisod\",\"ReserveeEmail\":\"fdgn sdjfg\",\"Passengers\":[{\"firstName\":\"i sgsdfi\",\"lastName\":\"dsfogijd\"},{\"firstName\":\"posdfo i\",\"lastName\":\"sfiojsdf g\"}]}");
     
        assertNotNull(deserializer.getReservator());
        assertEquals("dasfadlg", deserializer.getReservator().getFirstname());
        assertEquals("dasfadlg", deserializer.getReservator().getLastname());
        assertEquals("oisdjfisod", deserializer.getReservator().getPhone());
        assertEquals("fdgn sdjfg", deserializer.getReservator().getEmail());
    }

    @Test
    public void testGetFlightID() {
        ReservationDeserializer deserializer = new ReservationDeserializer("{\"flightID\":\"COL3257x100x2016-01-15T00:30:00.000Z\",\"numberOfSeats\":2,\"ReserveeName\":\"dasfadlg\",\"ReservePhone\":\"oisdjfisod\",\"ReserveeEmail\":\"fdgn sdjfg\",\"Passengers\":[{\"firstName\":\"i sgsdfi\",\"lastName\":\"dsfogijd\"},{\"firstName\":\"posdfo i\",\"lastName\":\"sfiojsdf g\"}]}");
        
        assertEquals("COL3257x100x2016-01-15T00:30:00.000Z", deserializer.getFlightID());
    }

    @Test
    public void testGetPassengers() {
        ReservationDeserializer deserializer = new ReservationDeserializer("{\"flightId\":\"COL3257x100x2016-01-15T00:30:00.000Z\",\"numberOfSeats\":2,\"ReserveeName\":\"dasfadlg\",\"ReservePhone\":\"oisdjfisod\",\"ReserveeEmail\":\"fdgn sdjfg\",\"Passengers\":[{\"firstName\":\"i sgsdfi\",\"lastName\":\"dsfogijd\"},{\"firstName\":\"posdfo i\",\"lastName\":\"sfiojsdf g\"}]}");
        
        assertEquals(2, deserializer.getPassengers().size());
    }
    
}
