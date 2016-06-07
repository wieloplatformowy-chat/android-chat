package pl.sggw.wzim.chat.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.sggw.wzim.chat.R;
import pl.sggw.wzim.chat.model.Contact;
import pl.sggw.wzim.chat.model.ContactListHeader;
import pl.sggw.wzim.chat.model.ContactListItem;

/**
 * Created by Michal on 07.06.2016.
 */
public class ContactListItemAdapter extends ArrayAdapter<ContactListItem>{

    private ArrayList<Integer> headerPositions = new ArrayList<>();

    public ContactListItemAdapter(Context context, int resource, int textViewResourceId, List<ContactListItem> objects) {
        super(context, resource, textViewResourceId, objects);
    }

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
            TextView textView = (TextView) convertView.findViewById(R.id.textView3);
            Contact c = (Contact)getItem(position);
            textView.setText(c.getName());
            if(!c.isAvailable()) {
                textView.setTextColor(ContextCompat.getColor(super.getContext(),R.color.secondary_text));
                ((ImageView)convertView.findViewById(R.id.imageView2)).setColorFilter(ContextCompat.getColor(super.getContext(),R.color.secondary_text));
                ((ImageView)convertView.findViewById(R.id.imageView3)).setColorFilter(ContextCompat.getColor(super.getContext(),R.color.secondary_text));
                ((ImageButton)convertView.findViewById(R.id.imageButton)).setColorFilter(ContextCompat.getColor(super.getContext(),R.color.secondary_text));
                convertView.findViewById(R.id.imageView4).setVisibility(View.GONE);
            }
        }

        return convertView;
    }

}
