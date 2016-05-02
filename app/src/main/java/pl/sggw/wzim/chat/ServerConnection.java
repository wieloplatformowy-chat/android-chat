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


    public BaseResponse register(String name, String password) {
        UserrestcontrollerApi api = new UserrestcontrollerApi();
        UserDto newUser = new UserDto();

        newUser.setName(name);
        newUser.setPassword(password);
        try {
            return api.registerUsingPOST(newUser);
        } catch (ApiException ex) {
          return getBaseResponsFromJSON(ex.getMessage());
        } catch (Exception ex) {
            BaseResponse exceptionalResponse = new BaseResponse();
            ResponseError exceptionalError = new ResponseError();

            exceptionalError.setId(-770);
            exceptionalError.setName(ex.toString());
            exceptionalError.setMessage(ex.getMessage());

            exceptionalResponse.setError(exceptionalError);
            exceptionalResponse.setSuccess(false);
            return new BaseResponse();
        }
    }

    public DataResponsestring login(String name, String password) {
        UserrestcontrollerApi api = new UserrestcontrollerApi();
        UserDto user = new UserDto();

        user.setName(name);
        user.setPassword(password);
        try {
            return api.loginUsingPOST(user);
        } catch (ApiException ex) {
             return getDataResponsestringFromJSON(ex.getMessage());
        } catch (Exception ex) {
            DataResponsestring exceptionalResponse = new DataResponsestring();
            ResponseError exceptionalError = new ResponseError();

            exceptionalError.setId(-770);
            exceptionalError.setName(ex.toString());
            exceptionalError.setMessage(ex.getMessage());

            exceptionalResponse.setError(exceptionalError);
            exceptionalResponse.setSuccess(false);

            return exceptionalResponse;
        }
    }

    private BaseResponse getBaseResponsFromJSON(String json) {
        try {
            return (BaseResponse)ApiInvoker.deserialize(json,"",BaseResponse.class);
        } catch (Exception ex){
            return new BaseResponse();
        }
    }

    private DataResponsestring getDataResponsestringFromJSON(String json){
        try {
            return (DataResponsestring)ApiInvoker.deserialize(json,"",DataResponsestring.class);
        } catch (Exception ex){
            return new DataResponsestring();
        }
    }

}
