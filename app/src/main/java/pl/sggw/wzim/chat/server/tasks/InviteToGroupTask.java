package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.GrouprestcontrollerApi;
import pl.sggw.wzim.chat.swagger.model.InviteToGroupParams;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class InviteToGroupTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostInviteToGroupCallback> mCallback;
    private String token;
    private InviteToGroupParams invitation;

    private int errorCode;
    private boolean inviteToGroupSuccess = false;

    public InviteToGroupTask(PostInviteToGroupCallback callback, InviteToGroupParams invitationParams, String authToken){
        mCallback = new WeakReference<>(callback);
        invitation = invitationParams;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        GrouprestcontrollerApi api = new GrouprestcontrollerApi();
        try {
            api.inviteUsingPOST(invitation,token);
            inviteToGroupSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostInviteToGroupCallback callback = mCallback.get();
        if (callback == null) return;

        if (inviteToGroupSuccess) callback.onInviteToGroupSuccess();
        else callback.onInviteToGroupFail(InviteToGroupError.fromErrorID(errorCode));
    }

    public interface PostInviteToGroupCallback {
        void onInviteToGroupSuccess();
        void onInviteToGroupFail(InviteToGroupError error);
    }

    public enum InviteToGroupError {
        UNKNOWN_ERROR(1),
        USER_NOT_EXISTS(101),
        LOGIN_REQUIRED(105);

        private int errorID;

        InviteToGroupError(int ID){
            errorID = ID;
        }

        public static InviteToGroupError fromErrorID(int ID){
            switch (ID){
                case 101: return USER_NOT_EXISTS;
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}
