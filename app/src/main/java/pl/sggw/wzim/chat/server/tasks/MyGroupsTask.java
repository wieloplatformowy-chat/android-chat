package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.GrouprestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class MyGroupsTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostMyGroupsCallback> mCallback;
    private String token;
    private List<ConversationResponse> groupConversations;

    private int errorCode;
    private boolean myGroupsSuccess = false;

    public MyGroupsTask(PostMyGroupsCallback callback, String authToken){
        mCallback = new WeakReference<>(callback);
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        GrouprestcontrollerApi api = new GrouprestcontrollerApi();
        try {
            groupConversations = api.myUsingGET(token);
            myGroupsSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostMyGroupsCallback callback = mCallback.get();
        if (callback == null) return;

        if (myGroupsSuccess) callback.onMyGroupsSuccess(groupConversations);
        else callback.onMyGroupsFail(MyGroupsError.fromErrorID(errorCode));
    }

    public interface PostMyGroupsCallback {
        void onMyGroupsSuccess(List<ConversationResponse> groupConversations);
        void onMyGroupsFail(MyGroupsError error);
    }

    public enum MyGroupsError {
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        MyGroupsError(int ID){
            errorID = ID;
        }

        public static MyGroupsError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}
