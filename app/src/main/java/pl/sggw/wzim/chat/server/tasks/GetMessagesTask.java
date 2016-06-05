package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.MessagerestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.MessageResponse;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class GetMessagesTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostGetMessageCallback> mCallback;
    private long ID;
    private String token;
    private List<MessageResponse> messages;

    private int errorCode;
    private boolean getMessagesSuccess = false;

    public GetMessagesTask(PostGetMessageCallback callback, long conversationID, String authToken){
        mCallback = new WeakReference<>(callback);
        ID = conversationID;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        MessagerestcontrollerApi api = new MessagerestcontrollerApi();

        try {
            messages = api.lastUsingGET(ID,token);
            getMessagesSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostGetMessageCallback callback = mCallback.get();
        if (callback == null) return;

        if (getMessagesSuccess) callback.onLoginSuccess(messages);
        else callback.onLoginFail(GetMessagesError.fromErrorID(errorCode));
    }

    public interface PostGetMessageCallback {
        void onLoginSuccess(List<MessageResponse> messages);
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
