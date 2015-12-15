package infrastructure;

import java.util.List;
import models.AirportModel;

public interface IAirportProvider {

    List<AirportModel> search(String query);
}
