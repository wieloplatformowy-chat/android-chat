package pl.sggw.wzim.chat;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contact_list, container, false);
        String[] data = new String[]{"Kontakt 1","Kontakt 2","Kontakt 3","Kontakt 4"};

        ListView lv1 = (ListView)root.findViewById(R.id.listView);
        ListView lv2 = (ListView)root.findViewById(R.id.listView2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(),R.layout.contact_list_row,R.id.textView3,data){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = super.getView(position, convertView, parent);
                ((TextView) row.findViewById(R.id.textView3)).setText(getItem(position));
                return row;
            }

        };

        lv1.setAdapter(adapter);
        lv2.setAdapter(adapter);

        setListViewHeightBasedOnChildren(lv1);
        setListViewHeightBasedOnChildren(lv2);

        return root;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
