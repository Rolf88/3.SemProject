/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.parsing.Parser;
import static com.jayway.restassured.path.json.JsonPath.from;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rest.ApplicationConfig;

/**
 *
 * @author AlexanderSteen
 */
public class FlightInfoTest {
        
    static Server server;

    public FlightInfoTest() {
        baseURI = "http://localhost:8084/Server";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        server = new Server(8082);
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(servletHolder, "/api/*");
        server.setHandler(contextHandler);
        server.start();
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        server.stop();
        //waiting for all the server threads to terminate so we can exit gracefully
        server.join();
    }
    
    @Test
    public void test_findFlightsFrom_illegalDate() throws ParseException{
        given().
                contentType("application/json").
                when().
                get("/flightinfo/AS/01-07-2001/3").
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3));
    }
    
    @Test
    public void test_findFlightsFrom_noFlight(){
        given().
                contentType("application/json").
                when().
                get("/flightinfo/AS/2001-07-04T12:08:56.235-0700/3").
                then().
                statusCode(404).
                body("httpError", equalTo(404)).
                body("errorCode", equalTo(1));
    }
    
    @Test
    public void test_findFlightsFrom_noSeats(){
        given().
                contentType("application/json").
                when().
                get("/flightinfo/AS/2001-07-04T12:08:56.235-0700/12").
                then().
                statusCode(404).
                body("httpError", equalTo(404)).
                body("errorCode", equalTo(2));
    }
    
    @Test
    public void test_findFlightsFrom_illegalSeatNumber(){
        given().
                contentType("application/json").
                when().
                get("/flightinfo/AS/2001-07-04T12:08:56.235-0700/hej").
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3));
    }
    
    @Test
    public void test_findFlightsFrom_illegalFrom(){
        given().
                contentType("application/json").
                when().
                get("/flightinfo/AGSW/2001-07-04T12:08:56.235-0700/1").
                then().
                statusCode(400).
                body("httpError", equalTo(400)).
                body("errorCode", equalTo(3));
    }
    
    @Test
    public void test_findFlightsFrom_successResponse(){
        given().
                contentType("application/json").
                when().
                get("/flightinfo/AA/2001-07-04T12:08:56.235-0700/1").
                then().
                statusCode(200);
    }
}
