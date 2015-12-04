/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.ArrayList;
import java.util.List;
import models.AirlineModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.flyfetcher.FlyFetcher;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author AlexanderSteen
 */
public class FlyFetcherTest {
    
    public FlyFetcherTest() {
    }
    
    @Test
    public void test_flyfetcher(){
        List<AirlineModel> am = new ArrayList();
        FlyFetcher ff = new FlyFetcher("http://angularairline-plaul.rhcloud.com/api/flightinfo/CPH/2016-01-22T00:00:00.000Z/4", am);
        ff.run();
        assertTrue(am.size() > 0);
    }
}
