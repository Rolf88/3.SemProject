package facades;

import infrastructure.IAirportProvider;
import java.util.List;
import models.AirportModel;
import org.junit.Test;
import static org.junit.Assert.*;

public class AeroAirportProviderTest {

    @Test
    public void testSearch() {
        IAirportProvider provider = new AeroAirportProvider("cfbdd6018b8a2e369bd541cc68950bad");
        List<AirportModel> airports = provider.search("Copen");

        assertEquals(1, airports.size());
        
        AirportModel copenhagenAirport = airports.get(0);
        assertEquals("CPH", copenhagenAirport.getIataCode());
        assertEquals("Copenhagen", copenhagenAirport.getCity());
        assertEquals("Denmark", copenhagenAirport.getCountry());
    }

}
