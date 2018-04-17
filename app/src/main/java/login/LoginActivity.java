package login;


import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.rubrunghi.dev.minequotes.MainActivity;
import com.rubrunghi.dev.minequotes.R;

import java.util.ArrayList;

import player.Player;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText user;
    private EditText passwd;
    private Button btn_login;
    LoginHandler handler;
    ArrayList<LoginProfiles> arrPlayer = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        user = (EditText) findViewById(R.id.user_field);
        passwd = (EditText) findViewById(R.id.passwd_field);
        btn_login = (Button) findViewById(R.id.logIn_btn);

        btn_login.setOnClickListener(this);
        handler = new LoginHandler();
        handler.getLogins("login");


    }


    @Override
    public void onClick(View v) {
        if (btn_login == v) {
            loginCheck();
        }
    }

    public static LoginProfiles loggedInUser;

    public void loginCheck() {
        ArrayList<LoginProfiles> profiles = handler.getLoginDatas();

        for (LoginProfiles profile : profiles) {
            if (profile.getUsername().equals(user.getText().toString()) && profile.getPassword().equals(passwd.getText().toString())) {
                Intent i = new Intent(this, MainActivity.class);

                loggedInUser = profile;
                startActivity(i);

                return;
            }
        }

        Toast.makeText(this, "Falsche Logindaten", Toast.LENGTH_LONG).show();
    }

}
