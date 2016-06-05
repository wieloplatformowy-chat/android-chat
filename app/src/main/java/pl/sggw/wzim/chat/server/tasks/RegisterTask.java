package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.UserrestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.RegisterParams;
import pl.sggw.wzim.chat.swagger.model.UserDto;

/**
 * @author Patryk Konieczny
 * @since 07.05.2016
 */
 public class RegisterTask extends AsyncTask<Void, Void, Void> {

    private WeakReference<PostRegistrationCallback> mCallback;
    private String userEmail;
    private String userName;
    private String userPassword;

    private int errorCode;
    private boolean registerSuccess = false;

    public RegisterTask(PostRegistrationCallback callback, String email, String name, String password) {
        mCallback = new WeakReference<>(callback);
        userEmail = email;
        userName = name;
        userPassword = password;
    }

    protected Void doInBackground(Void... params) {
        UserrestcontrollerApi api = new UserrestcontrollerApi();
        RegisterParams newUser = new RegisterParams();

        newUser.setEmail(userEmail);
        newUser.setName(userName);
        newUser.setPassword(userPassword);
        try {
            api.registerUsingPOST(newUser);
            registerSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostRegistrationCallback callback = mCallback.get();
        if (callback == null) return;

        if (registerSuccess) callback.onRegistrationSuccess();
        else callback.onRegistrationFail(RegisterError.fromErrorID(errorCode));
    }

    public interface PostRegistrationCallback {
        void onRegistrationSuccess();
        void onRegistrationFail(RegisterError error);
    }

    public enum RegisterError{
        UNKNOWN_ERROR(1),
        USERNAME_IS_TAKEN(103);

        private int errorID;

        RegisterError(int ID){
            errorID = ID;
        }

        public static RegisterError fromErrorID(int ID){
            switch (ID) {
                case 103: return USERNAME_IS_TAKEN;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}