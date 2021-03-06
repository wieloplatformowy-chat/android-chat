package pl.sggw.wzim.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.sggw.wzim.chat.server.ServerConnection;
import pl.sggw.wzim.chat.server.tasks.RegisterTask;

public class RegisterActivity extends AppCompatActivity implements RegisterTask.PostRegistrationCallback{

    private EditText loginEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText password2EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        loginEditText = (EditText) findViewById(R.id.login_input);
        emailEditText = (EditText) findViewById(R.id.email_input);
        passwordEditText = (EditText) findViewById(R.id.password_input);
        password2EditText = (EditText) findViewById(R.id.password2_input);

        Button buttonOne = (Button) findViewById(R.id.register_button);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                registerButtonClicked();
            }
        });

        Button buttonTwo = (Button) findViewById(R.id.login_button);
        buttonTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent toNextPage = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toNextPage);
            }
        });
    }

    void registerButtonClicked() {
        String loginInput = readText(loginEditText);
        String emailInput = readText(emailEditText);
        String passwordInput = readText(passwordEditText);
        String password2Input = readText(password2EditText);

        if(validateEmail(emailInput) && validatePassword(passwordInput, password2Input)) {
            ServerConnection.getInstance().register(RegisterActivity.this, emailInput, loginInput, password2Input);
            findViewById(R.id.register_button).setEnabled(false);
        }
        else {
            if(!validateEmail(emailInput)) emailEditText.setError(getString(R.string.invalidInput));
            if(!validatePassword(passwordInput, password2Input)) password2EditText.setError(getString(R.string.invalidInput));
        }
    }

    String readText(EditText editText) {
        return editText.getText().toString().trim();
    }

    boolean validateEmail(String email) {
        return email.contains("@");
    }

    boolean validatePassword(String password1, String password2){
        return password1.equals(password2);
    }

    @Override
    public void onRegistrationSuccess() {
        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_success),
                Toast.LENGTH_LONG).show();
        findViewById(R.id.register_button).setEnabled(true);
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    @Override
    public void onRegistrationFail(RegisterTask.RegisterError error) {
        String message = "";

        switch (error){
            case UNKNOWN_ERROR: message = getString(R.string.unknown_error);
                break;
            case USERNAME_IS_TAKEN: message = getString(R.string.username_taken);
                break;
        }

        Toast.makeText(RegisterActivity.this, message,
                Toast.LENGTH_LONG).show();
        findViewById(R.id.register_button).setEnabled(true);
    }
}
