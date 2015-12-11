/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.UserEntity;
import exceptions.DataAllreadyExistException;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;
import org.eclipse.jetty.server.Authentication.User;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author RolfMoikjær
 */
public class UserFacadeTest {

    UserFacade userFacade;

    @Before
    public void setUp() {
        userFacade = new UserFacade(Persistence.createEntityManagerFactory("ServerPUTest").createEntityManager());
    }

    @Test
    public void testGetUserByUserId() {
        UserEntity user = userFacade.getUserByUserId("1");

        assertNotNull(user);
    }

    @Test
    public void testGetUsers() {
        List<UserEntity> users = userFacade.getUsers();

        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetUserByUserName() {
        UserEntity user = userFacade.getUserByEmail("test");

        assertNotNull(user);
        assertEquals("test", user.getEmail());
    }

    @Test
    public void testAuthenticateUser_valid() throws Exception {
        List<String> roles = userFacade.authenticateUser("test", "test");

        assertFalse(roles.isEmpty());
    }

    @Test
    public void testAuthenticateUser_invalid() throws Exception {
        List<String> roles = userFacade.authenticateUser("test", "testasd");

        assertNull(roles);
    }

    @Test
    public void testCreateUser() throws Exception {
        List<String> roles = new ArrayList<>();
        roles.add("admin");

        int usersBefore = userFacade.getUsers().size();

        UserEntity user = new UserEntity();
        user.setEmail("createUser");
        user.setFirstname("mister");
        user.setLastname("sister");
        user.setPassword("password");
        user.setPhone("45875465");

        userFacade.createUser(user);

        assertEquals(usersBefore + 1, userFacade.getUsers().size());
    }

    @Test
    public void testDeleteUser() throws DataAllreadyExistException, NoSuchAlgorithmException, InvalidKeySpecException {
        List<String> roles = new ArrayList<>();
        roles.add("admin");

        UserEntity user = new UserEntity();
        user.setEmail("deleteUser");
        user.setFirstname("deleteUser");
        user.setLastname("deletersen");
        user.setPassword("password");
        user.setPhone("45875487");

        userFacade.createUser(user);

        int usersBefore = userFacade.getUsers().size();
        userFacade.deleteUser(userFacade.getUserByEmail("deleteUser"));

        assertEquals(usersBefore - 1, userFacade.getUsers().size());
    }
}
