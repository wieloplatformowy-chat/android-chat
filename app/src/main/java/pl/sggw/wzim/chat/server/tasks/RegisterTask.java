package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.UserrestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.UserDto;

/**
 * @author Patryk Konieczny
 * @since 07.05.2016
 */
 public class RegisterTask extends AsyncTask<String, Void, String> {

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

    public interface PostRegistrationCallback {
        void onRegistrationSuccess(String message);
        void onRegistrationFail(String message);
    }
}