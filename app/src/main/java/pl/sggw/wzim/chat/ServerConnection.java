package pl.sggw.wzim.chat;

import pl.sggw.wzim.chat.swagger.*;
import pl.sggw.wzim.chat.swagger.model.*;
import pl.sggw.wzim.chat.swagger.api.*;

/**
 * @author Patryk Konieczny
 * @since 02.05.2016
 */
public class ServerConnection {
    private static ServerConnection ourInstance = new ServerConnection();

    public static ServerConnection getInstance() {
        return ourInstance;
    }

    private ServerConnection() {
    }


    public RestResponse registerUsingPOST(String name, String password) {
        UserrestcontrollerApi api = new UserrestcontrollerApi();
        UserDto newUser = new UserDto();

        newUser.setName(name);
        newUser.setPassword(password);
        try {
            return api.registerUsingPOST(newUser);
        } catch (ApiException ex) {
            RestResponse exceptionResponse = new RestResponse();
            exceptionResponse.setResponse(ex.getMessage());
            return exceptionResponse;
        }
    }

    public TokenDto login(String name, String password) {
        UserrestcontrollerApi api = new UserrestcontrollerApi();
        LoginDto user = new LoginDto();

        user.setName(name);
        user.setPassword(password);
        try {
            return api.loginUsingPOST(user);
        } catch (ApiException ex) {
            return null;
        }
    }


}
