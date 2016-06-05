package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.UserrestcontrollerApi;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class LogoutTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostLogoutCallback> mCallback;
    private String token;

    private int errorCode;
    private boolean logoutSuccess = false;

    public LogoutTask(PostLogoutCallback callback, String authToken){
        mCallback = new WeakReference<>(callback);
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        UserrestcontrollerApi api = new UserrestcontrollerApi();

        try {
            api.logoutUsingGET(token);
            logoutSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostLogoutCallback callback = mCallback.get();
        if (callback == null) return;

        if (logoutSuccess) callback.onLogoutSuccess();
        else callback.onLogoutFail(LogoutError.fromErrorID(errorCode));
    }

    public interface PostLogoutCallback {
        void onLogoutSuccess();
        void onLogoutFail(LogoutError error);
    }

    public enum LogoutError{
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        LogoutError(int ID){
            errorID = ID;
        }

        public static LogoutError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}