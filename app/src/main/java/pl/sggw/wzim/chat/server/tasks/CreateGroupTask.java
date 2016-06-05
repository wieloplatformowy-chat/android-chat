package pl.sggw.wzim.chat.server.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.api.GrouprestcontrollerApi;

/**
 * @author Patryk Konieczny
 * @since 05.06.2016
 */
public class CreateGroupTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<PostCreateGroupCallback> mCallback;
    private String token;
    private Long groupID;

    private int errorCode;
    private boolean createGroupSuccess = false;

    public CreateGroupTask(PostCreateGroupCallback callback, String authToken){
        mCallback = new WeakReference<>(callback);;
        token = authToken;
    }

    protected Void doInBackground(Void... params){
        GrouprestcontrollerApi api = new GrouprestcontrollerApi();
        try {
            groupID = api.createUsingGET(token).getId();
            createGroupSuccess = true;
        } catch (ApiException ex) {
            JSONObject exceptionResponse = new JSONObject(ex.getMessage());
            errorCode = exceptionResponse.getInt("id");
        } finally {
            return null;
        }
    }

    protected void onPostExecute(Void result) {
        PostCreateGroupCallback callback = mCallback.get();
        if (callback == null) return;

        if (createGroupSuccess) callback.onCreateGroupSuccess(groupID);
        else callback.onCreateGroupFail(CreateGroupError.fromErrorID(errorCode));
    }

    public interface PostCreateGroupCallback {
        void onCreateGroupSuccess(Long groupID);
        void onCreateGroupFail(CreateGroupError error);
    }

    public enum CreateGroupError {
        UNKNOWN_ERROR(1),
        LOGIN_REQUIRED(105);

        private int errorID;

        CreateGroupError(int ID){
            errorID = ID;
        }

        public static CreateGroupError fromErrorID(int ID){
            switch (ID){
                case 105: return LOGIN_REQUIRED;
                default: return UNKNOWN_ERROR;
            }
        }
    }
}