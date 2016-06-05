package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.UserrestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.UserResponse;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class WhoAmITask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostWhoAmICallback> mCallback;
    private String token;
    private UserResponse theUserIAm;

    private int errorCode;
    private boolean checkWhoAmISuccess = false;

    public WhoAmITask(PostWhoAmICallback callback, String authToken){
        mCallback = new WeakReference<>(callback);
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        UserrestcontrollerApi api = new UserrestcontrollerApi();

        try {
            theUserIAm = api.whoAmIUsingGET(token);
            checkWhoAmISuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostWhoAmICallback callback = mCallback.get();
        if (callback == null) return;

        if (checkWhoAmISuccess) callback.onLoginSuccess(theUserIAm);
        else callback.onLoginFail(GetMessagesError.fromErrorID(errorCode));
    }

    public interface PostWhoAmICallback {
        void onLoginSuccess(UserResponse UserData);
        void onLoginFail(GetMessagesError error);
    }

    public enum GetMessagesError{
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        GetMessagesError(int ID){
            errorID = ID;
        }

        public static GetMessagesError fromErrorID(int ID){
            switch (ID){
                case 1: return UNKNOWN_ERROR;
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}