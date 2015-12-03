package test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PersistenceHelper {

    private static EntityManager entityManager;

    static {
        entityManager = Persistence.createEntityManagerFactory("ServerPU").createEntityManager();
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
