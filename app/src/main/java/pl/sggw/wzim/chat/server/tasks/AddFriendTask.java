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
public class AddFriendTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostAddFriendCallback> mCallback;
    private Long ID;
    private String token;

    private int errorCode;
    private boolean addFriendSuccess = false;

    public AddFriendTask(PostAddFriendCallback callback, Long userID, String authToken){
        mCallback = new WeakReference<>(callback);
        ID = userID;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        FriendrestcontrollerApi api = new FriendrestcontrollerApi();
        try {
            api.addUsingPOST(ID,token);
            addFriendSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostAddFriendCallback callback = mCallback.get();
        if (callback == null) return;

        if (addFriendSuccess) callback.onAddFriendSuccess();
        else callback.onAddFriendFail(AddFriendError.fromErrorID(errorCode));
    }

    public interface PostAddFriendCallback {
        void onAddFriendSuccess();
        void onAddFriendFail(AddFriendError error);
    }

    public enum AddFriendError {
        UNKNOWN_ERROR(1),
        USER_NOT_EXISTS(101),
        LOGIN_REQUIRED(105),
        ALREADY_A_FRIEND(106);

        private int errorID;

        AddFriendError(int ID){
            errorID = ID;
        }

        public static AddFriendError fromErrorID(int ID){
            switch (ID){
                case 101: return USER_NOT_EXISTS;
                case 105: return LOGIN_REQUIRED;
                case 106: return ALREADY_A_FRIEND;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}
