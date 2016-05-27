package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.server.ServerConnection;
import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.UserrestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.LoginDto;
import pl.sggw.wzim.chat.swagger.model.LoginParams;

/**
 * @author Patryk Konieczny
 * @since 07.05.2016
 */
public class LoginTask extends AsyncTask<Void, Void, Void> {

    private WeakReference<PostLoginCallback> mCallback;
    private String userName;
    private String userPassword;


    private int errorCode;
    private boolean loginSuccess = false;

    public LoginTask(PostLoginCallback callback, String name, String password){
        mCallback = new WeakReference<>(callback);
        userName = name;
        userPassword = password;
    }

    protected Void doInBackground(Void... params){
        UserrestcontrollerApi api = new UserrestcontrollerApi();
        LoginParams user = new LoginParams();

        user.setName(userName);
        user.setPassword(userPassword);
        try {
            ServerConnection.getInstance().setUserToken(api.loginUsingPOST(user));
            loginSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostLoginCallback callback = mCallback.get();
        if (callback == null) return;

        if (loginSuccess) callback.onLoginSuccess();
        else callback.onLoginFail(LoginError.fromErrorID(errorCode));
    }

    public interface PostLoginCallback {
        void onLoginSuccess();
        void onLoginFail(LoginError error);
    }

    public enum LoginError{
        UNKNOWN_ERROR(1),
        USER_NOT_EXISTS(101),
        INVALID_PASSWORD(102);

        private int errorID;

        LoginError(int ID){
            errorID = ID;
        }

        public static LoginError fromErrorID(int ID){
            switch (ID){
                case 1: return UNKNOWN_ERROR;
                case 101: return USER_NOT_EXISTS;
                case 102: return INVALID_PASSWORD;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}
