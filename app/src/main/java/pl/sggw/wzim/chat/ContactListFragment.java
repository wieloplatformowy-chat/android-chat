package pl.sggw.wzim.chat;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.sggw.wzim.chat.adapters.ContactListItemAdapter;
import pl.sggw.wzim.chat.model.ContactGroup;
import pl.sggw.wzim.chat.server.ChatService;
import pl.sggw.wzim.chat.server.ServerConnection;
import pl.sggw.wzim.chat.server.tasks.GetConversationTask;
import pl.sggw.wzim.chat.server.tasks.IsOnlineTask;
import pl.sggw.wzim.chat.server.tasks.LoginTask;
import pl.sggw.wzim.chat.server.tasks.MyFriendsTask;
import pl.sggw.wzim.chat.server.tasks.MyGroupsTask;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;
import pl.sggw.wzim.chat.swagger.model.UserResponse;
import pl.sggw.wzim.chat.model.Contact;
import pl.sggw.wzim.chat.model.ContactListHeader;
import pl.sggw.wzim.chat.model.ContactListItem;

public class ContactListFragment extends Fragment implements AdapterView.OnItemClickListener, MyFriendsTask.PostMyFriendsCallback, MyGroupsTask.PostMyGroupsCallback, IsOnlineTask.PostIsOnlineCallback, ChatService.UnreadConversationServiceCallback, GetConversationTask.PostGetConversationCallback {

    private List<UserResponse> mFriendList;


    @Override
    public void onMyFriendsSuccess(List<UserResponse> friendList) {
        if(friendList.size() == 0) return;
        mFriendList = friendList;

        data.add(new ContactListHeader("Kontakty"));
        for(UserResponse response: friendList){
            ServerConnection.getInstance().isOnline(this, response.getId());
            ServerConnection.getInstance().GetConversation(this,response.getId());
            Contact contact = new Contact(response.getAvatar(), response.getName() ,false);
            contact.setId(response.getId());
            data.add(contact);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMyFriendsFail(MyFriendsTask.MyFriendsError error) {
    }


    @Override
    public void onMyGroupsSuccess(List<ConversationResponse> groupConversations) {
        if(groupConversations.size() == 0) return;

        data.add(new ContactListHeader("Grupy"));
        for(ConversationResponse response: groupConversations){
            ArrayList<Contact> participants = new ArrayList<>();

            for(UserResponse userResponse: response.getUsers())
                participants.add(new Contact(null, userResponse.getName(),true));

            ContactGroup group = new ContactGroup(null, response.getName(), participants, true); //TODO: add online status check for groups
            data.add(group);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMyGroupsFail(MyGroupsTask.MyGroupsError error) {

    }



    @Override
    public void onIsOnlineSuccess(boolean IsOnline, long userID) {
        for(ContactListItem item: data){
            Contact contact = null;
            try{
                contact = (Contact) item;
            }catch (ClassCastException e){

            }

            if(contact != null && contact.getId() == userID) {
                contact.setAvailable(IsOnline);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onIsOnlineFail(IsOnlineTask.IsOnlineError error) {

    }

    @Override
    public void onNewUnreadConversation(List<Long> conversationsIDs) {
        for(ContactListItem item: data){
            long id = ((Contact)item).getConversationID();
            if(conversationsIDs.contains(id)){
                ((Contact)item).setAvailable(true);
            }
        }
    }

    @Override
    public void onGetConversationsSuccess(ConversationResponse conversation, long userID) {
        for(ContactListItem item: data){
            Contact contact = null;
            try{
                contact = (Contact) item;
            }catch (ClassCastException e){

            }

            if(contact != null && contact.getId() == userID) {
                contact.setConversationID(conversation.getId());
                break;
            }
        }

    }

    @Override
    public void onGetConversationsFail(GetConversationTask.GetConversationsError error) {

    }

    public interface OnContactSelectedListener{
        void onContactSelected(Contact contact);
    }

    private OnContactSelectedListener listener;
    private ContactListItemAdapter adapter;
    private ArrayList<ContactListItem> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contact_list, container, false);
        data = new ArrayList<>();

        ListView lv1 = (ListView)root.findViewById(R.id.listView);
        lv1.setOnItemClickListener(this);

        ServerConnection.getInstance().myFriends(this);
        ServerConnection.getInstance().myGroups(this);

        adapter = new ContactListItemAdapter(root.getContext(),R.layout.contact_list_row,R.id.textView3,data);

        lv1.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        return root;
    }

    public void setListener(OnContactSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContactListItem item = adapter.getItem(position);
        if(!item.isSectionHeader() && listener != null)
            listener.onContactSelected((Contact)item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ChatActivity chatActivity = (ChatActivity) context;

        if(chatActivity != null)
            listener = chatActivity;
    }

}
