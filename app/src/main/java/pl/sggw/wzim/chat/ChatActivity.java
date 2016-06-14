package pl.sggw.wzim.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.sggw.wzim.chat.model.Contact;
import pl.sggw.wzim.chat.adapters.DrawerListAdapter;
import pl.sggw.wzim.chat.model.ContactGroup;
import pl.sggw.wzim.chat.server.ChatService;
import pl.sggw.wzim.chat.server.ServerConnection;

public class ChatActivity extends AppCompatActivity implements ContactListFragment.OnContactSelectedListener {

    private boolean mTwoPane = true;
    public static final String SELECTED_CONTACT_KEY = "contact key";

    private ArrayList<Contact>  drawerListData;
    private DrawerListAdapter drawerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_drawer);



        if(findViewById(R.id.fragmentContainerM) == null){
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            FragmentManager supportManager = getSupportFragmentManager();
            if(mTwoPane){
                supportManager.beginTransaction().add(R.id.fragmentContainerM,new ContactListFragment()).commit();
                supportManager.beginTransaction().add(R.id.fragmentContainerD, new ChatFragment()).commit();
            }else{
                supportManager.beginTransaction().add(R.id.fragmentContainer, new ContactListFragment()).commit();
                setDrawerLocked(true);
            }
        }

        Bitmap launcherImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        drawerListData = new ArrayList<>();

        findViewById(R.id.drawerListHeader).setVisibility(View.INVISIBLE);

        ListView listView = (ListView)findViewById(R.id.drawerListView);
        drawerListAdapter = new DrawerListAdapter(this,R.layout.drawer_list_row,R.id.drawer_name,drawerListData);
        listView.setAdapter(drawerListAdapter);

        restoreToolbar();
    }

    private void setDrawerLocked(boolean isDrawerLocked){
        if(isDrawerLocked){
            ((DrawerLayout)findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else{
            ((DrawerLayout)findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            clearDrawerListAndHide();
            setDrawerLocked(true);
            restoreToolbar();
        }
    }


    private void clearDrawerListAndHide(){
        findViewById(R.id.drawerListHeader).setVisibility(View.INVISIBLE);
        drawerListData.clear();
        drawerListAdapter.notifyDataSetChanged();
    }

    private void restoreToolbar(){
        TextView title = (TextView) findViewById(R.id.include).findViewById(R.id.textView2);
        title.setText(ServerConnection.getInstance().getLoggedUser().getName());
        ((ImageView)findViewById(R.id.imageView5)).setImageBitmap(ServerConnection.getInstance().getLoggedUser().getAvatar());
    }

    @Override
    public void onContactSelected(Contact contact) {
        clearDrawerListAndHide();

        if(!mTwoPane) {
            ChatFragment chatFragment = new ChatFragment();
            Bundle data = new Bundle();
            data.putParcelable(SELECTED_CONTACT_KEY,contact);
            chatFragment.setArguments(data);
            setDrawerLocked(false);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, chatFragment).addToBackStack(null).commit();
        }

        TextView title = (TextView) findViewById(R.id.include).findViewById(R.id.textView2);
        title.setText(contact.getName());

        if(contact instanceof ContactGroup)
        {
            ContactGroup selectedGroup = (ContactGroup) contact;

            if(selectedGroup.getParticipants().size() != 0){
                findViewById(R.id.drawerListHeader).setVisibility(View.VISIBLE);
                for(Contact participant: selectedGroup.getParticipants()){
                    drawerListData.add(participant);
                }
                drawerListAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    protected void onStop() {
        ChatService.getInstance().stopChatService();
        super.onStop();
    }
}
