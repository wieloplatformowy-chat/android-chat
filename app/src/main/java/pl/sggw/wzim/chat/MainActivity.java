package pl.sggw.wzim.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mMessageInputForm;
    private ArrayAdapter<String> mInputFormAdapter;
    private ArrayList<String> mListData;
    private final static String LIST_DATA_KEY = "list data key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMessageInputForm = (EditText) findViewById(R.id.messageInputForm);

        ListView messageList = (ListView) findViewById(R.id.messageList);

        if(savedInstanceState != null)
            mListData = savedInstanceState.getStringArrayList(LIST_DATA_KEY);
        else
            mListData =  new ArrayList<>();

        mInputFormAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,mListData);
        messageList.setAdapter(mInputFormAdapter);

        findViewById(R.id.sendMessageButton).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sendMessageButton){
            String message = mMessageInputForm.getText().toString();
            sendMessageIfNotEmpty(message);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(LIST_DATA_KEY,mListData);
    }

    private void sendMessageIfNotEmpty(String message){
        if(!message.isEmpty()){
            mMessageInputForm.setText("");
            mInputFormAdapter.add(message);
            mInputFormAdapter.notifyDataSetChanged();
        }
    }
}
