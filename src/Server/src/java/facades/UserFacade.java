package facades;

import infrastructure.IUserService;
import entity.UserEntity;
import exceptions.DataAllreadyExistException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import models.ReservationModel;
import security.PasswordHash;

public class UserFacade implements IUserService {

    private final EntityManager entityManager;

    public UserFacade(EntityManagerFactory factory) {
        this.entityManager = factory.createEntityManager();

    }

    @Override
    public UserEntity getUserByUserId(String id) {
        return this.entityManager.find(UserEntity.class, Long.parseLong(id));
    }

    @Override
    public List<UserEntity> getUsers() {
        Query createQuery = this.entityManager.createQuery("SELECT u FROM User u");

        return createQuery.getResultList();
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        Query createQuery = this.entityManager.createQuery("SELECT u FROM UserProfile u WHERE u.email = :email");
        createQuery.setParameter("email", email);

        try {
            return (UserEntity) createQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> authenticateUser(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserEntity user = getUserByEmail(email);
        return user != null && PasswordHash.validatePassword(password, user.getPassword()) ? user.getRoles() : null;
    }

    @Override
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

    @Override
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

    @Override
    public List<ReservationModel> getReservations(Long userId) {
        Query query = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.user.userid = :id");
        query.setParameter("id", userId);
        return query.getResultList();
    }
}
