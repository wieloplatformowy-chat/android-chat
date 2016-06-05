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

import pl.sggw.wzim.chat.R;

public class ContactListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public interface OnContactSelectedListener{
        void onContactSelected();
    }

    private OnContactSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bitmap placeholderPicture = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        View root = inflater.inflate(R.layout.fragment_contact_list, container, false);
        ContactListItem[] data = new ContactListItem[6];
        data[0] = new ContactListHeader("Grupy");
        data[1] = new Contact(placeholderPicture, "Michal", true);
        data[2] = new Contact(placeholderPicture, "Michal", true);
        data[3] = new ContactListHeader("Kontakty");
        data[4] = new Contact(placeholderPicture,"Michal",true);
        data[5] = new Contact(placeholderPicture,"Michal",true);

        ListView lv1 = (ListView)root.findViewById(R.id.listView);
        lv1.setOnItemClickListener(this);

        ArrayAdapter<ContactListItem> adapter = new ArrayAdapter<ContactListItem>(root.getContext(),R.layout.contact_list_row,R.id.textView3,data){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (getItem(position).isSectionHeader()) {
                    // if section header

                    convertView = inflater.inflate(R.layout.contact_list_header, parent, false);
                    TextView headerText = (TextView) convertView.findViewById(R.id.headerTextView);
                    headerText.setText(((ContactListHeader)getItem(position)).getText());
                }
                else {
                    // if item
                    convertView = inflater.inflate(R.layout.contact_list_row, parent, false);
                    ((TextView) convertView.findViewById(R.id.textView3)).setText(((Contact)getItem(position)).getName());

                    //temporary solution; onItemClick is not called
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(listener != null)
                                listener.onContactSelected();
                        }
                    });
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
        if(listener != null)
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
