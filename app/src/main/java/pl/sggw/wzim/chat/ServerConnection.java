package pl.sggw.wzim.chat;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;

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


    public void register(PostRegistrationCallback context,String email, String name, String password) {
        (new RegisterTask(context)).execute(email, name, password);
    }

    public void login(PostLoginCallback context, String name, String password) {
        (new LoginTask(context)).execute(name,password);
    }

    private class RegisterTask extends AsyncTask<String, Void, String>{

        private WeakReference<PostRegistrationCallback> mCallback;
        private String message;
        private boolean registerSuccess = false;

        public RegisterTask(PostRegistrationCallback callback) {
            mCallback = new WeakReference<>(callback);
        }

        protected String doInBackground(String... userData) {
            UserrestcontrollerApi api = new UserrestcontrollerApi();
            UserDto newUser = new UserDto();

            newUser.setEmail(userData[0]);
            newUser.setName(userData[1]);
            newUser.setPassword(userData[2]);
            try {
                message = api.registerUsingPOST(newUser).getResponse();
                registerSuccess = true;
            } catch (ApiException ex) {
                message = ex.getMessage();
            } catch (Exception ex){
                message = ex.getMessage();
            } finally {
                return message;
            }
        }

        protected void onPostExecute(String registerResult) {
            PostRegistrationCallback callback = mCallback.get();
            if (callback == null) return;

            if (registerSuccess) callback.onRegistrationSuccess(message);
            else callback.onRegistrationFail(message);
        }

    }

    public interface PostRegistrationCallback {
        void onRegistrationSuccess(String message);
        void onRegistrationFail(String message);
    }

    private class LoginTask extends AsyncTask<String, Void, String>{

        private WeakReference<PostLoginCallback> mCallback;
        private String result;
        private boolean loginSuccess = false;

        public LoginTask(PostLoginCallback callback){
            mCallback = new WeakReference<>(callback);
        }

        protected String doInBackground(String... userData){
            UserrestcontrollerApi api = new UserrestcontrollerApi();
            LoginDto user = new LoginDto();

            user.setName(userData[0]);
            user.setPassword(userData[1]);
            try {
                result = api.loginUsingPOST(user).getToken();
                loginSuccess = true;
            } catch (ApiException ex) {
                result = ex.getMessage();
            } catch (Exception ex) {
                result = ex.getMessage();
            } finally {
                return result;
            }
        }

        protected void onPostExecute(String loginResult) {
            PostLoginCallback callback = mCallback.get();
            if (callback == null) return;

            if (loginSuccess) callback.onLoginSuccess(result, "Zalogowano pomyślnie.");
            else callback.onLoginFail(result);
        }
    }
    public interface PostLoginCallback {
        void onLoginSuccess(String token, String message);
        void onLoginFail(String message);
    }




}
