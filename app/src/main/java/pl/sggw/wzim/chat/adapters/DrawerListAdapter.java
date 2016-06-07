package pl.sggw.wzim.chat.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.sggw.wzim.chat.R;
import pl.sggw.wzim.chat.model.Contact;


public class DrawerListAdapter extends ArrayAdapter<Contact> {

    private Context context;

    public DrawerListAdapter(Context context, int rowLayout, int textViewResource, ArrayList<Contact> objects) {
        super(context, rowLayout, textViewResource, objects);

        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);
        Contact currentItem = getItem(position);
        ((TextView)row.findViewById(R.id.drawer_name)).setText(currentItem.getName());
        if(!currentItem.isAvailable())
            ((ImageView)row.findViewById(R.id.drawer_avatar)).setColorFilter(context.getResources().getColor(R.color.secondary_text));

        row.findViewById(R.id.drawer_remove_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Deleted!!!",Toast.LENGTH_SHORT).show();
            }
        });
        return row;
    }
}
