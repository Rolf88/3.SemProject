package infrastructure;

import java.util.Date;
import java.util.List;
import models.HighscoreModel;

public interface ISearchRepository {

    List<HighscoreModel<String>> getTopDestination(int size);
    
    void add(String origin, String destination, Date departureDate, int numberOfPassengers, Date timeCreated);
}
