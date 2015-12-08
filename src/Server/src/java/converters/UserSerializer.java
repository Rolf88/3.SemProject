/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.UserEntity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RolfMoikjær
 */
public class UserSerializer {

    private static JsonObject userToJsonObject(UserEntity user) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", user.getId());
        obj.addProperty("email", user.getEmail());
        obj.addProperty("phone", user.getPhone());
        obj.addProperty("password", user.getPassword());
        if (user.getRoles() != null) {
            for (String roles : user.getRoles()) {
                obj.addProperty("roles", roles);

            }
        }
        return obj;
    }

    public static String userToJson(UserEntity user) {
        return new Gson().toJson(userToJsonObject(user));
    }

    public static String userToJson(List<UserEntity> users) {
        JsonArray usersArr = new JsonArray();
        for (UserEntity user : users) {
            usersArr.add(userToJsonObject(user));
        }

        return new Gson().toJson(usersArr);
    }
}
