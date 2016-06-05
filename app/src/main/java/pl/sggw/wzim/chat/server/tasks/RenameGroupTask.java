package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.GrouprestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;
import pl.sggw.wzim.chat.swagger.model.RenameGroupParams;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class RenameGroupTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostRenameGroupFCallback> mCallback;
    private String token;
    private RenameGroupParams renameParams;

    private int errorCode;
    private boolean renameGroupSuccess = false;

    public RenameGroupTask(PostRenameGroupFCallback callback, RenameGroupParams renameGroupParams, String authToken){
        mCallback = new WeakReference<>(callback);
        renameParams = renameGroupParams;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        GrouprestcontrollerApi api = new GrouprestcontrollerApi();
        try {
            api.renameUsingPOST(renameParams,token);
            renameGroupSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostRenameGroupFCallback callback = mCallback.get();
        if (callback == null) return;

        if (renameGroupSuccess) callback.onRenameGroupSuccess();
        else callback.onRenameGroupFail(RenameGroupError.fromErrorID(errorCode));
    }

    public interface PostRenameGroupFCallback {
        void onRenameGroupSuccess();
        void onRenameGroupFail(RenameGroupError error);
    }

    public enum RenameGroupError {
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        RenameGroupError(int ID){
            errorID = ID;
        }

        public static RenameGroupError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}

