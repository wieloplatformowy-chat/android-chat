package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.UserrestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.LoginDto;

/**
 * @author Patryk Konieczny
 * @since 07.05.2016
 */
public  class LoginTask extends AsyncTask<String, Void, String> {

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

    public interface PostLoginCallback {
        void onLoginSuccess(String token, String message);
        void onLoginFail(String message);
    }
}
