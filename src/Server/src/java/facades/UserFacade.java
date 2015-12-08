package facades;

import entity.UserEntity;
import exceptions.DataAllreadyExistException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import security.PasswordHash;

public class UserFacade {

    private final EntityManager entityManager;

    public UserFacade(EntityManagerFactory factory) {
        this.entityManager = factory.createEntityManager();

    }

    public UserEntity getUserByUserId(String id) {
        return this.entityManager.find(UserEntity.class, Long.parseLong(id));
    }

    public List<UserEntity> getUsers() {
        Query createQuery = this.entityManager.createQuery("SELECT u FROM User u");

        return createQuery.getResultList();
    }

    public UserEntity getUserByEmail(String email) {
        Query createQuery = this.entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email");
        createQuery.setParameter("email", email);

        try {
            return (UserEntity) createQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> authenticateUser(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserEntity user = getUserByEmail(email);
        return user != null && PasswordHash.validatePassword(password, user.getPassword()) ? user.getRoles() : null;
    }

    public UserEntity createUser(UserEntity user) throws DataAllreadyExistException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }

        user.addRole("User");
        user.setPassword(PasswordHash.createHash(user.getPassword()));
        UserEntity oldUser = getUserByEmail(user.getEmail());
        if (oldUser != null) {
            throw new DataAllreadyExistException("Email already in use");
        }

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();

        return user;
    }

    public void deleteUser(UserEntity user) {
        if (user == null) {
            throw new NullPointerException("user cannot be null");
        }

        this.entityManager.getTransaction().begin();
        this.entityManager.remove(user);
        this.entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
    }
}
