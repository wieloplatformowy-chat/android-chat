package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.MessagerestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.SendMessageParams;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class SendMessageTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<SendMessageCallback> mCallback;
    private SendMessageParams message;
    private String token;

    private int errorCode;
    private boolean SendMessageSuccess = false;

    public SendMessageTask(SendMessageCallback callback, SendMessageParams messageParams, String authToken){
        mCallback = new WeakReference<>(callback);
        message = messageParams;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        MessagerestcontrollerApi api = new MessagerestcontrollerApi();

        try {
            api.sendUsingPOST(message,token);
            SendMessageSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        SendMessageCallback callback = mCallback.get();
        if (callback == null) return;

        if (SendMessageSuccess) callback.onSendMessageSuccess();
        else callback.onSendMessageFail(SendMessageError.fromErrorID(errorCode));
    }

    public interface SendMessageCallback {
        void onSendMessageSuccess();
        void onSendMessageFail(SendMessageError error);
    }

    public enum SendMessageError {
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        SendMessageError(int ID){
            errorID = ID;
        }

        public static SendMessageError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}