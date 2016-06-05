package pl.sggw.wzim.chat.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import pl.sggw.wzim.chat.R;

public class ChatActivity extends AppCompatActivity implements ContactListFragment.OnContactSelectedListener {

    boolean mTwoPane = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(getAvatarDrawable());

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
            }
        }

        //nav drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Bitmap launcherImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        ArrayList<Contact> data = new ArrayList<>();
        data.add(new Contact(launcherImage,"Johnny",true));
        data.add(new Contact(launcherImage,"Jenny",true));
        data.add(new Contact(launcherImage,"Josh",false));

        ListView listView = (ListView)findViewById(R.id.drawerListView);
        listView.setAdapter(new DrawerListAdapter(this,R.layout.drawer_list_row,R.id.drawer_name,data));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private Drawable getAvatarDrawable(){
        Bitmap launcherImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap statusImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_status_online);
        Bitmap finalImage = Bitmap.createBitmap(launcherImage.getWidth(), launcherImage.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(finalImage);
        canvas.drawBitmap(launcherImage,0,0,null);
        canvas.drawBitmap(statusImage , canvas.getWidth()-statusImage.getWidth(), canvas.getHeight()-statusImage.getHeight() , null);

        return new BitmapDrawable(getResources(), finalImage);
    }

    @Override
    public void onContactSelected() {
        if(!mTwoPane)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ChatFragment()).addToBackStack(null).commit();
    }
}
