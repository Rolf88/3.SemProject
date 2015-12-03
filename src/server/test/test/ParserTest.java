/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.ReservatorModel;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author priva_000
 */
public class ParserTest {

    public ParserTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_CanParseReservationRequest() {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse("{    \"flightID\": \"MCA2345\",    \"numberOfSeats\": 2,    \"ReserveeName\": \"Peter Hansen\",    \"ReservePhone\": \"12345678\",    \"ReserveeEmail\": \"peter@peter.dk\",    \"Passengers\": [        {            \"firstName\": \"Peter\",            \"lastName\": \"Peterson\" },        {            \"firstName\": \"Jane\",            \"lastName\": \"Peterson\" } ]}");
        JsonObject obj = element.getAsJsonObject();

        ReservatorModel reservator = new ReservatorModel(obj.get("ReserveeName").getAsString(), obj.get("ReserveeName").getAsString(), obj.get("ReserveeEmail").getAsString(), obj.get("ReservePhone").getAsString());

        assertEquals("Peter Hansen", reservator.getFirstname());
        assertEquals("peter@peter.dk", reservator.getEmail());
        assertEquals("12345678", reservator.getPhone());
    }

}
