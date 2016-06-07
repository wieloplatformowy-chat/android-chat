package pl.sggw.wzim.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pl.sggw.wzim.chat.mock.MockProfileInfo;
import pl.sggw.wzim.chat.server.ServerConnection;
import pl.sggw.wzim.chat.server.tasks.GetConversationTask;
import pl.sggw.wzim.chat.server.tasks.GetLastMessagesTask;
import pl.sggw.wzim.chat.server.tasks.LoginTask;
import pl.sggw.wzim.chat.server.tasks.SendMessageTask;
import pl.sggw.wzim.chat.server.tasks.WhoAmITask;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;
import pl.sggw.wzim.chat.swagger.model.MessageResponse;
import pl.sggw.wzim.chat.model.Message;
import pl.sggw.wzim.chat.adapters.MessageAdapter;
import pl.sggw.wzim.chat.swagger.model.UserResponse;

public class ChatFragment extends Fragment implements View.OnClickListener, GetLastMessagesTask.PostGetMessageCallback,GetConversationTask.PostGetConversationCallback, SendMessageTask.SendMessageCallback {

    private EditText mMessageInputForm;
    private final static String LIST_DATA_KEY = "list data key";

    private ArrayList<Message> mMessageList;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    private Long conversationID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        mMessageInputForm = (EditText) root.findViewById(R.id.messageInputForm);

//        if(savedInstanceState != null)
//            mMessageList = savedInstanceState.getParcelableArrayList(LIST_DATA_KEY);
//        else
            mMessageList =  new ArrayList<>();

        ServerConnection.getInstance().GetConversation(this,1831L); // TODO: receive user id from activity

        root.findViewById(R.id.sendMessageButton).setOnClickListener(this);

        recyclerView = (RecyclerView) root.findViewById(R.id.messageRecyclerView);

        messageAdapter = new MessageAdapter(mMessageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageAdapter);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sendMessageButton){
            String message = mMessageInputForm.getText().toString();
            sendMessageIfNotEmpty(message);
        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(LIST_DATA_KEY,mMessageList);
//    }

    private void sendMessageIfNotEmpty(String message){
        if(!message.isEmpty()){
            mMessageInputForm.setText("");

            Calendar calendarInstance = Calendar.getInstance();
            String timestamp = calendarInstance.get(Calendar.HOUR_OF_DAY) + ":" + calendarInstance.get(Calendar.MINUTE);
            messageAdapter.addMessage(new Message(message,timestamp, MockProfileInfo.getLoggedUser()));
            messageAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(messageAdapter.getItemCount()-1);

            ServerConnection instance = ServerConnection.getInstance();
            instance.SendMessage(this,conversationID,message);
        }
    }

    @Override
    public void onGetMessageSuccess(List<MessageResponse> messages) {
        if(messages.size() != 0)

        for(MessageResponse response: messages){
            Message message = new Message(response.getMessage(),response.getDate().toString().substring(12,20),String.valueOf(response.getUserId()));
            messageAdapter.addMessage(message);
        }
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMessageFail(GetLastMessagesTask.GetMessagesError error) {

    }


    @Override
    public void onGetConversationsSuccess(ConversationResponse conversation) {
        conversationID = conversation.getId();
        ServerConnection.getInstance().getLastMessages(this, conversationID);
    }

    @Override
    public void onGetConversationsFail(GetConversationTask.GetConversationsError error) {

    }

    @Override
    public void onSendMessageSuccess() {

    }

    @Override
    public void onSendMessageFail(SendMessageTask.SendMessageError error) {

    }

}
