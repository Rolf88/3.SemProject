/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.ArrayList;
import java.util.List;
import models.AirlineInternalModel;
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
        List<AirlineInternalModel> aim = new ArrayList();
        FlyFetcher ff = new FlyFetcher("http://angularairline-plaul.rhcloud.com/api/flightinfo/CPH/2016-01-22T00:00:00.000Z/4", aim,"http://angularairline-plaul.rhcloud.com/");
        ff.run();
        assertTrue(aim.size() > 0);
    }
}
