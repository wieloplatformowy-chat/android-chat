package pl.sggw.wzim.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void register(View view){
        Toast.makeText(RegisterActivity.this, "Rejestracja tez pewnie bedzie kiedys dzialac",
                Toast.LENGTH_LONG).show();
        //setContentView(R.layout.jakis_inny);
    }

    public void changeLayout(View view){
        Intent toNextPage = new Intent(this, LoginActivity.class);
        startActivity(toNextPage);
    }
}
