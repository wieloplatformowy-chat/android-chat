package pl.sggw.wzim.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText iLogin;
    EditText iEmail;
    EditText iPassword;
    EditText iPassword2;

    String inputLogin = "-";
    String inputEmail = "-";
    String inputPassword = "-";
    String inputPassword2 = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        iLogin = (EditText) findViewById(R.id.login_input);
        iEmail = (EditText) findViewById(R.id.email_input);
        iPassword = (EditText) findViewById(R.id.password_input);
        iPassword2 = (EditText) findViewById(R.id.password2_input);

        final Button buttonOne = (Button) findViewById(R.id.register_button);
        assert buttonOne != null;
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                registerButtonClicked();
            }
        });

        final Button buttonTwo = (Button) findViewById(R.id.login_button);
        assert buttonTwo != null;
        buttonTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent toNextPage = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toNextPage);
            }
        });
    }

    void registerButtonClicked() {
        inputLogin = stringInput(iLogin, inputLogin);
        inputEmail = stringInput(iEmail, inputEmail);
        inputPassword = stringInput(iPassword, inputPassword);
        inputPassword2 = stringInput(iPassword2, inputPassword2);

        if(validateEmail(inputEmail) && validatePassword(inputPassword, inputPassword2)) {
            Toast.makeText(RegisterActivity.this, "Rejestracja: " + inputLogin + ", " + inputEmail + ", " + inputPassword + ", " + inputPassword2,
                    Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(RegisterActivity.this, R.string.invalidInput, Toast.LENGTH_LONG).show();
    }

    String stringInput(EditText et, String s) {
        if (et.getText().toString().trim().length() > 0) {
            s = et.getText().toString().trim();
        }
        return s;
    }

    boolean validateEmail(String s) {
        boolean b = false;
        if (s.equals(inputEmail) && s.contains("@")) b = true;
        return  b;
    }

    boolean validatePassword(String p1, String p2){
        boolean b = false;
        if(p1.equals(p2)) b = true;
        return b;
    }
}
