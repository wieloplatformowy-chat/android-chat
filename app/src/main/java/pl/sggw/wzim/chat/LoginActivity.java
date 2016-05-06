package pl.sggw.wzim.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginEditText = (EditText)findViewById(R.id.login_input);
        passwordEditText = (EditText)findViewById(R.id.password_input);

        Button loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                loginButtonCLicked();
            }
        });

        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent toNextPage = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toNextPage);
            }
        });
    }

    void loginButtonCLicked(){
        String loginInput = readText(loginEditText);
        String passwordInput = readText(passwordEditText);

        Toast.makeText(LoginActivity.this, "Logowanie pewnie bedzie kiedys dzialac: " + loginInput + ", " + passwordInput,
                Toast.LENGTH_LONG).show();
    }

    String readText(EditText editText) {
        return editText.getText().toString().trim();
    }
}
