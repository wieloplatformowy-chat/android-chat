package pl.sggw.wzim.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.sggw.wzim.chat.server.tasks.LoginTask;
import pl.sggw.wzim.chat.server.ServerConnection;

public class LoginActivity extends AppCompatActivity implements LoginTask.PostLoginCallback {

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

        ServerConnection.getInstance().login(LoginActivity.this,loginInput,passwordInput);
        findViewById(R.id.login_button).setEnabled(false);
    }

    String readText(EditText editText) {
        return editText.getText().toString().trim();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(LoginActivity.this, getString(R.string.login_success),
                Toast.LENGTH_LONG).show();
        findViewById(R.id.login_button).setEnabled(true);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onLoginFail(LoginTask.LoginError error) {
        String message = "";

        switch (error){
            case UNKNOWN_ERROR: message = getString(R.string.unknown_error);
                break;
            case USER_NOT_EXISTS: message = getString(R.string.user_not_exists);
                break;
            case INVALID_PASSWORD: message = getString(R.string.invalid_password);
                break;
        }
        Toast.makeText(LoginActivity.this, message,
                Toast.LENGTH_SHORT).show();
        findViewById(R.id.login_button).setEnabled(true);
    }
}
