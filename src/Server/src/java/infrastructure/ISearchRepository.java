package infrastructure;

import java.util.Date;

public interface ISearchRepository {

    void add(String origin, String destination, Date departureDate, int numberOfPassengers, Date timeCreated);
}
