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
        resetDatabase();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void execute(String[] commands) {

        entityManager.getTransaction().begin();

        for (String command : commands) {
            Query query = entityManager.createNativeQuery(command);
            query.executeUpdate();
        }

        entityManager.getTransaction().commit();
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

        entityManager.getTransaction().commit();
    }

    public static Long count(String sql) {
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }
}
