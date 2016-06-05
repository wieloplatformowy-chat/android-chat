package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.FriendrestcontrollerApi;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class DeleteFriendTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostDeleteFriendCallback> mCallback;
    private Long ID;
    private String token;

    private int errorCode;
    private boolean deleteFriendSuccess = false;

    public DeleteFriendTask(PostDeleteFriendCallback callback, Long userID, String authToken){
        mCallback = new WeakReference<>(callback);
        ID = userID;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        FriendrestcontrollerApi api = new FriendrestcontrollerApi();
        try {
            api.deleteUsingDELETE(ID,token);
            deleteFriendSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostDeleteFriendCallback callback = mCallback.get();
        if (callback == null) return;

        if (deleteFriendSuccess) callback.onDeleteFriendSuccess();
        else callback.onDeleteFriendFail(DeleteFriendError.fromErrorID(errorCode));
    }

    public interface PostDeleteFriendCallback {
        void onDeleteFriendSuccess();
        void onDeleteFriendFail(DeleteFriendError error);
    }

    public enum DeleteFriendError {
        UNKNOWN_ERROR(1),
        USER_NOT_EXISTS(101),
        LOGIN_REQUIRED(105);

        private int errorID;

        DeleteFriendError(int ID){
            errorID = ID;
        }

        public static DeleteFriendError fromErrorID(int ID){
            switch (ID){
                case 101: return USER_NOT_EXISTS;
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}

