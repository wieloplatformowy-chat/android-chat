package pl.sggw.wzim.chat.server.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.UserrestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.SearchUserParams;
import pl.sggw.wzim.chat.swagger.model.UserResponse;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class SearchUserTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostSearchUserCallback> mCallback;
    private String token;
    private SearchUserParams searchParams;
    private List<UserResponse> users;

    private Bitmap defAvatar;

    private int errorCode;
    private boolean searchUserSuccess = false;

    public SearchUserTask(PostSearchUserCallback callback, SearchUserParams searchedUser, String authToken, Bitmap defaultAvatar){
        mCallback = new WeakReference<>(callback);
        searchParams = searchedUser;
        token = authToken;
        defAvatar = defaultAvatar;
    }

    protected Void doInBackground(Void... params){
        UserrestcontrollerApi api = new UserrestcontrollerApi();

        try {
            users = api.searchUsingPOST(searchParams, token);
            for (UserResponse user:
                 users) {
             if (user.getAvatar() == null) user.setAvatar(defAvatar);
            }
            searchUserSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostSearchUserCallback callback = mCallback.get();
        if (callback == null) return;

        if (searchUserSuccess) callback.onSearchUserSuccess(users);
        else callback.onSearchUserFail(SearchUserError.fromErrorID(errorCode));
    }

    public interface PostSearchUserCallback {
        void onSearchUserSuccess(List<UserResponse> results);
        void onSearchUserFail(SearchUserError error);
    }

    public enum SearchUserError{
        UNKNOWN_ERROR(1),
        USER_NOT_EXISTS(101),
        LOGIN_REQUIRED(105);

        private int errorID;

        SearchUserError(int ID){
            errorID = ID;
        }

        public static SearchUserError fromErrorID(int ID){
            switch (ID){
                case 101: return USER_NOT_EXISTS;
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}
