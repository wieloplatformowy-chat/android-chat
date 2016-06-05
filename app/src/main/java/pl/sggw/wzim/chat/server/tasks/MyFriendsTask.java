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
public class MyFriendsTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostMyFriendsCallback> mCallback;
    private String token;
    private List<UserResponse> friends;

    private int errorCode;
    private boolean myFriendsSuccess = false;

    public MyFriendsTask(PostMyFriendsCallback callback, String authToken){
        mCallback = new WeakReference<>(callback);;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        FriendrestcontrollerApi api = new FriendrestcontrollerApi();
        try {
            friends = api.myUsingGET(token);
            myFriendsSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostMyFriendsCallback callback = mCallback.get();
        if (callback == null) return;

        if (myFriendsSuccess) callback.onMyFriendsSuccess(friends);
        else callback.onMyFriendsFail(MyFriendsError.fromErrorID(errorCode));
    }

    public interface PostMyFriendsCallback {
        void onMyFriendsSuccess(List<UserResponse> friendList);
        void onMyFriendsFail(MyFriendsError error);
    }

    public enum MyFriendsError {
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        MyFriendsError(int ID){
            errorID = ID;
        }

        public static MyFriendsError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}

