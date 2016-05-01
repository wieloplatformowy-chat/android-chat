package pl.sggw.wzim.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void login(View view){
        Toast.makeText(LoginActivity.this, "Logowanie pewnie bedzie kiedys dzialac",
                Toast.LENGTH_LONG).show();
    }

    public void changeLayout(View view){
        Intent toNextPage = new Intent(this, RegisterActivity.class);
        startActivity(toNextPage);
    }
}
