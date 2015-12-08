package infrastructure;

import entity.UserEntity;
import exceptions.DataAllreadyExistException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IUserService {

    List<String> authenticateUser(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    UserEntity createUser(UserEntity user) throws DataAllreadyExistException, NoSuchAlgorithmException, InvalidKeySpecException;

    void deleteUser(UserEntity user);

    UserEntity getUserByEmail(String email);

    UserEntity getUserByUserId(String id);

    List<UserEntity> getUsers();
    
}
