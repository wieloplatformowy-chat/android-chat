package pl.sggw.wzim.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText iLogin;
    EditText iPassword;
    String inputLogin = "-";
    String inputPassword = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        iLogin = (EditText)findViewById(R.id.login_input);
        iPassword = (EditText)findViewById(R.id.password_input);

        final Button buttonOne = (Button) findViewById(R.id.login_button);

        assert buttonOne != null;
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                loginButtonCLicked();
            }
        });

        final Button buttonTwo = (Button) findViewById(R.id.register_button);
        assert buttonTwo != null;
        buttonTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent toNextPage = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toNextPage);
            }
        });
    }

    void loginButtonCLicked(){
        inputLogin = stringInput(iLogin, inputLogin);
        inputPassword = stringInput(iPassword, inputPassword);

        Toast.makeText(LoginActivity.this, "Logowanie pewnie bedzie kiedys dzialac: " + inputLogin + ", " + inputPassword,
                Toast.LENGTH_LONG).show();
    }

    String stringInput(EditText et, String s){
        if (et.getText().toString().trim().length() > 0) {
            s = et.getText().toString().trim();
        }
        return s;
    }
}
