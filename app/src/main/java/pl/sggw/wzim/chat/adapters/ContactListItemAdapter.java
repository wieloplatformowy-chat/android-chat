package pl.sggw.wzim.chat.adapters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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
            Contact c = (Contact)getItem(position);
            convertView = inflater.inflate(R.layout.contact_list_row, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.textView3);
            textView.setText(c.getName());
            ImageView avatar = (ImageView) convertView.findViewById(R.id.imageView2);
            avatar.setImageBitmap(c.getProfilePicture());
            if(!c.isAvailable()) {
                textView.setTextColor(ContextCompat.getColor(super.getContext(),R.color.secondary_text));

                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter grayscaleFilter = new ColorMatrixColorFilter(matrix);

                ((ImageView)convertView.findViewById(R.id.imageView2)).setColorFilter(grayscaleFilter);
                ((ImageView)convertView.findViewById(R.id.imageView3)).setColorFilter(grayscaleFilter);
                ((ImageButton)convertView.findViewById(R.id.imageButton)).setColorFilter(grayscaleFilter);
                convertView.findViewById(R.id.imageView4).setVisibility(View.GONE);
            }

            if(c.isNewMessage()){
                (convertView.findViewById(R.id.imageView3)).setVisibility(View.VISIBLE);
            }else{
                (convertView.findViewById(R.id.imageView3)).setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

}
