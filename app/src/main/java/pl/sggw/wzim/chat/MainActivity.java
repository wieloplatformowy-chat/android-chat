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

    EditText messageInputForm;
    ArrayAdapter<String> inputFormAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageInputForm = (EditText) findViewById(R.id.messageInputForm);
        ListView listView = (ListView) findViewById(R.id.messageList);
        inputFormAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(inputFormAdapter);

        findViewById(R.id.sendMessageButton).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sendMessageButton){
            String message = messageInputForm.getText().toString();
            inputFormAdapter.add(message);
            inputFormAdapter.notifyDataSetChanged();
        }
    }
}
