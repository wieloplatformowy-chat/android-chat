package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.FriendrestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.UserResponse;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class IsOnlineTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostIsOnlineCallback> mCallback;
    private Long ID;
    private String token;
    private boolean online;

    private int errorCode;
    private boolean IsOnlineSuccess = false;

    public IsOnlineTask(PostIsOnlineCallback callback, Long userID, String authToken){
        mCallback = new WeakReference<>(callback);
        ID = userID;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        FriendrestcontrollerApi api = new FriendrestcontrollerApi();
        try {
            online = api.onlineUsingGET(ID, token).getOnline();
            IsOnlineSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostIsOnlineCallback callback = mCallback.get();
        if (callback == null) return;

        if (IsOnlineSuccess) callback.onIsOnlineSuccess(online, ID);
        else callback.onIsOnlineFail(IsOnlineError.fromErrorID(errorCode));
    }

    public interface PostIsOnlineCallback {
        void onIsOnlineSuccess(boolean IsOnline, long userID);
        void onIsOnlineFail(IsOnlineError error);
    }

    public enum IsOnlineError {
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        IsOnlineError(int ID){
            errorID = ID;
        }

        public static IsOnlineError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}