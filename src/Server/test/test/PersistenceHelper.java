package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PersistenceHelper {

    private static final EntityManager entityManager;

    static {
        entityManager = Persistence.createEntityManagerFactory("ServerPU").createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void resetDatabase() {
        String[] tables = {
            "reservation_passenger",
            "passenger",
            "reservation",
            "flight",
            "airport",
            "airline"
        };

        entityManager.getTransaction().begin();

        for (String tableName : tables) {
            Query query = entityManager.createNativeQuery("DELETE FROM " + tableName);

            query.executeUpdate();
        }

        String[] commands = {
            "INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Kastrup lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'ROM', 'Rom lufthavn');",
            "INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'OSL', 'Oslo lufthavn');",
            "INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (1, 100, CURRENT_TIMESTAMP, 20, 1, 1, 2);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (2, 100, CURRENT_TIMESTAMP, 20, 1, 1, 3);",
            "INSERT INTO flight (id, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (3, 100, CURRENT_TIMESTAMP, 20, 1, 1, 4);"
        };

        for (String command : commands) {
            Query query = entityManager.createNativeQuery(command);
            query.executeUpdate();
        }

        entityManager.getTransaction().commit();
    }
}
