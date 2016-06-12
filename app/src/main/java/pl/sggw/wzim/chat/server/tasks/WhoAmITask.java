package pl.sggw.wzim.chat.server.tasks;

import android.graphics.Bitmap;
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

    private Bitmap defAvatar;

    private int errorCode;
    private boolean checkWhoAmISuccess = false;

    public WhoAmITask(PostWhoAmICallback callback, String authToken, Bitmap defaultAvatar){
        mCallback = new WeakReference<>(callback);
        token = authToken;
        defAvatar = defaultAvatar;
    }

    protected Void doInBackground(Void... params){
        UserrestcontrollerApi api = new UserrestcontrollerApi();

        try {
            theUserIAm = api.whoAmIUsingGET(token);
            if (theUserIAm.getAvatar() == null) theUserIAm.setAvatar(defAvatar);
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

        if (checkWhoAmISuccess) callback.onWhoAmISuccess(theUserIAm);
        else callback.onWhoAmIFail(WhoAmIError.fromErrorID(errorCode));
    }

    public interface PostWhoAmICallback {
        void onWhoAmISuccess(UserResponse UserData);
        void onWhoAmIFail(WhoAmIError error);
    }

    public enum WhoAmIError{
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        WhoAmIError(int ID){
            errorID = ID;
        }

        public static WhoAmIError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}