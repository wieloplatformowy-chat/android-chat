package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.ConversationrestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class GetConversationTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostGetConversationCallback> mCallback;
    private Long ID;
    private String token;
    private ConversationResponse conversation;

    private int errorCode;
    private boolean getConversationSuccess = false;

    public GetConversationTask(PostGetConversationCallback callback, Long userID, String authToken){
        mCallback = new WeakReference<>(callback);
        ID = userID;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        ConversationrestcontrollerApi api = new ConversationrestcontrollerApi();
        try {
            conversation = api.getUsingGET(ID,token);
            getConversationSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostGetConversationCallback callback = mCallback.get();
        if (callback == null) return;

        if (getConversationSuccess) callback.onGetConversationsSuccess(conversation, ID);
        else callback.onGetConversationsFail(GetConversationsError.fromErrorID(errorCode));
    }

    public interface PostGetConversationCallback {
        void onGetConversationsSuccess(ConversationResponse conversation, long userID);
        void onGetConversationsFail(GetConversationsError error);
    }

    public enum GetConversationsError {
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        GetConversationsError(int ID){
            errorID = ID;
        }

        public static GetConversationsError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}