package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.MessagerestcontrollerApi;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class GetUnreadMessagesTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostGetUnreadMessagesCallback> mCallback;
    private String token;
    private List<Long> conversationsID;

    private int errorCode;
    private boolean getEarlierMessagesSuccess = false;

    public GetUnreadMessagesTask(PostGetUnreadMessagesCallback callback, String authToken){
        mCallback = new WeakReference<>(callback);
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        MessagerestcontrollerApi api = new MessagerestcontrollerApi();
        try {
            conversationsID = api.unreadUsingGET(token);
            getEarlierMessagesSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostGetUnreadMessagesCallback callback = mCallback.get();
        if (callback == null) return;

        if (getEarlierMessagesSuccess) callback.onGetUnreadMessagesSuccess(conversationsID);
        else callback.onGetUnreadMessagesFail(GetUnreadMessagesError.fromErrorID(errorCode));
    }

    public interface PostGetUnreadMessagesCallback {
        void onGetUnreadMessagesSuccess(List<Long> conversationsIDs);
        void onGetUnreadMessagesFail(GetUnreadMessagesError error);
    }

    public enum GetUnreadMessagesError {
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        GetUnreadMessagesError(int ID){
            errorID = ID;
        }

        public static GetUnreadMessagesError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}