package pl.sggw.wzim.chat;

import pl.sggw.wzim.chat.swagger.*;
import pl.sggw.wzim.chat.swagger.model.*;
import pl.sggw.wzim.chat.swagger.api.*;

/**
 * Created by Patryk on 30.04.2016.
 */
public class ServerConnection {
    private static ServerConnection ourInstance = new ServerConnection();

    public static ServerConnection getInstance() {
        return ourInstance;
    }

    private ServerConnection() {
    }

    public String register(String name, String password) {
        UserrestcontrollerApi api = new UserrestcontrollerApi();
        UserDto newUser = new UserDto();


        newUser.setName(name);
        newUser.setPassword(password);
        try {
            BaseResponse serverResponse = api.registerUsingPOST(newUser);
            return serverResponse.toString();
        } catch (ApiException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String login(String name, String password) {
        UserrestcontrollerApi api = new UserrestcontrollerApi();
        UserDto user = new UserDto();
        user.setName(name);
        user.setPassword(password);
        try {
            DataResponsestring serverResponse = api.loginUsingPOST(user);
            return serverResponse.toString();
        } catch (ApiException ex) {
            return ex.getMessage();
        }
    }
}
