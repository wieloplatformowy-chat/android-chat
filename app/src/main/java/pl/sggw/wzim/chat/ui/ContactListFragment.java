package pl.sggw.wzim.chat.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.sggw.wzim.chat.R;
import pl.sggw.wzim.chat.server.ServerConnection;
import pl.sggw.wzim.chat.server.tasks.AddFriendTask;
import pl.sggw.wzim.chat.server.tasks.LoginTask;
import pl.sggw.wzim.chat.server.tasks.MyFriendsTask;
import pl.sggw.wzim.chat.server.tasks.MyGroupsTask;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;
import pl.sggw.wzim.chat.swagger.model.UserResponse;

public class ContactListFragment extends Fragment implements AdapterView.OnItemClickListener, MyFriendsTask.PostMyFriendsCallback, MyGroupsTask.PostMyGroupsCallback, LoginTask.PostLoginCallback {

    @Override
    public void onMyFriendsSuccess(List<UserResponse> friendList) {
        if(friendList.size() == 0) return;

        data.add(new ContactListHeader("Kontakty"));
        for(UserResponse response: friendList){
            data.add(new Contact(null, response.getName() ,true));
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

    public interface OnContactSelectedListener{
        void onContactSelected();
    }

    private OnContactSelectedListener listener;
    private ArrayAdapter<ContactListItem> adapter;
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

        adapter = new ArrayAdapter<ContactListItem>(root.getContext(),R.layout.contact_list_row,R.id.textView3,data){

            private ArrayList<Integer> headerPositions = new ArrayList<>();

            @Override
            public boolean isEnabled(int position) {
                return !headerPositions.contains(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {


                LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (getItem(position).isSectionHeader()) {
                    // if section header
                    convertView = inflater.inflate(R.layout.contact_list_header, parent, false);
                    TextView headerText = (TextView) convertView.findViewById(R.id.headerTextView);
                    headerText.setText(((ContactListHeader)getItem(position)).getText());
                    headerPositions.add(position);
                }
                else {
                    // if item
                    convertView = inflater.inflate(R.layout.contact_list_row, parent, false);
                    ((TextView) convertView.findViewById(R.id.textView3)).setText(((Contact)getItem(position)).getName());
                }

                return convertView;
            }

        };

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
