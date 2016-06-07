package pl.sggw.wzim.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.sggw.wzim.chat.adapters.ContactListItemAdapter;
import pl.sggw.wzim.chat.server.ServerConnection;
import pl.sggw.wzim.chat.server.tasks.IsOnlineTask;
import pl.sggw.wzim.chat.server.tasks.LoginTask;
import pl.sggw.wzim.chat.server.tasks.MyFriendsTask;
import pl.sggw.wzim.chat.server.tasks.MyGroupsTask;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;
import pl.sggw.wzim.chat.swagger.model.UserResponse;
import pl.sggw.wzim.chat.model.Contact;
import pl.sggw.wzim.chat.model.ContactListHeader;
import pl.sggw.wzim.chat.model.ContactListItem;

public class ContactListFragment extends Fragment implements AdapterView.OnItemClickListener, MyFriendsTask.PostMyFriendsCallback, MyGroupsTask.PostMyGroupsCallback, LoginTask.PostLoginCallback, IsOnlineTask.PostIsOnlineCallback {

    private List<UserResponse> mFriendList;


    @Override
    public void onMyFriendsSuccess(List<UserResponse> friendList) {
        if(friendList.size() == 0) return;
        mFriendList = friendList;

        data.add(new ContactListHeader("Kontakty"));
        for(UserResponse response: friendList){
            ServerConnection.getInstance().IsOnline(this, response.getId());
            Contact contact = new Contact(null, response.getName() ,false);
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
            data.add(new Contact(null, response.getName() ,true));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMyGroupsFail(MyGroupsTask.MyGroupsError error) {

    }

    @Override
    public void onLoginSuccess() {
        ServerConnection.getInstance().MyFriends(this);
        ServerConnection.getInstance().MyGroups(this);
    }

    @Override
    public void onLoginFail(LoginTask.LoginError error) {

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

    public interface OnContactSelectedListener{
        void onContactSelected();
    }

    private OnContactSelectedListener listener;
    private ContactListItemAdapter adapter;
    private ArrayList<ContactListItem> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bitmap placeholderPicture = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        View root = inflater.inflate(R.layout.fragment_contact_list, container, false);
//        ContactListItem[] data = new ContactListItem[6];
//        data[0] = new ContactListHeader("Kontakty");
//        data[1] = new Contact(placeholderPicture, "Michal", true);
//        data[2] = new Contact(placeholderPicture, "Michal", true);
//        data[3] = new ContactListHeader("Grupy");
//        data[4] = new Contact(placeholderPicture,"Michal",true);
//        data[5] = new Contact(placeholderPicture,"Michal",true);
        data = new ArrayList<>();

        //wykonuja sie obydwa w tym samym czasie; ok?
        ServerConnection.getInstance().login(this,"ObiektTestowy4","test");


        ListView lv1 = (ListView)root.findViewById(R.id.listView);
        lv1.setOnItemClickListener(this);

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
        if(!adapter.getItem(position).isSectionHeader() && listener != null)
            listener.onContactSelected();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ChatActivity chatActivity = (ChatActivity) context;

        if(chatActivity != null)
            listener = chatActivity;
    }
}
