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
public class GetEarlierMessagesTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostGetEarlierMessagesCallback> mCallback;
    private long ID;
    private long messageID;
    private String token;
    private List<MessageResponse> messages;

    private int errorCode;
    private boolean getEarlierMessagesSuccess = false;

    public GetEarlierMessagesTask(PostGetEarlierMessagesCallback callback, long lastMessageID, long conversationID, String authToken){
        mCallback = new WeakReference<>(callback);
        messageID = lastMessageID;
        ID = conversationID;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        MessagerestcontrollerApi api = new MessagerestcontrollerApi();
        try {
            messages = api.beforeUsingGET(ID, messageID,token);
            getEarlierMessagesSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostGetEarlierMessagesCallback callback = mCallback.get();
        if (callback == null) return;

        if (getEarlierMessagesSuccess) callback.onGetEarlierMessagesSuccess(messages);
        else callback.onGetEarlierMessagesFail(GetEarlierMessagesError.fromErrorID(errorCode));
    }

    public interface PostGetEarlierMessagesCallback {
        void onGetEarlierMessagesSuccess(List<MessageResponse> messages);
        void onGetEarlierMessagesFail(GetEarlierMessagesError error);
    }

    public enum GetEarlierMessagesError{
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        GetEarlierMessagesError(int ID){
            errorID = ID;
        }

        public static GetEarlierMessagesError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}