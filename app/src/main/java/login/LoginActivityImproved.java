package login;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;



import com.rubrunghi.dev.minequotes.R;

import java.util.ArrayList;


public class LoginActivityImproved extends AppCompatActivity implements View.OnClickListener {


    private EditText user;
    private EditText passwd;
    private Button btn_login;
    private CheckBox checkAutoLogin;
    LoginHandlerImproved handler;
    SharedPreferences preferences;
    SharedPreferences.Editor sharedEditor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        user = (EditText) findViewById(R.id.user_field);
        passwd = (EditText) findViewById(R.id.passwd_field);
        btn_login = (Button) findViewById(R.id.logIn_btn);
        btn_login.setOnClickListener(this);
        checkAutoLogin = (CheckBox) findViewById(R.id.autologin);
        handler = new LoginHandlerImproved(this);

        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        sharedEditor = preferences.edit();
        checkAutoLogin();
    }

    public void checkAutoLogin() {
        String autologin = preferences.getString("login.autologin", "false");

        if(autologin.equals("true")) {
            checkAutoLogin.setChecked(true);
            String user = preferences.getString("login.user", "");
            String pw = preferences.getString("login.pw", "");
            btn_login.setActivated(false);
            handler.sendDataToServer(user, pw);
        }else {
            checkAutoLogin.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (btn_login == v){
           loginCheck();
        }
    }

    public static LoginProfiles loggedInUser;

    public void loginCheck() {
        String username = user.getText().toString().trim();
        String pw = passwd.getText().toString();
//        String username = "Hannes";
//        String pw = "123";
        saveCredentials(username, pw);
        handler.sendDataToServer(username, pw);

    }

    private void saveCredentials(String username, String pw) {
        if(checkAutoLogin.isChecked()) {
            sharedEditor.putString("login.autologin", "true");
            sharedEditor.putString("login.user", username);
            sharedEditor.putString("login.pw", pw);

        }else {
            sharedEditor.putString("login.autologin", "false");
            sharedEditor.putString("login.user", "");
            sharedEditor.putString("login.pw", "");
        }
        sharedEditor.commit();

    }
    public static void eraseCredentials(SharedPreferences.Editor sharedEditor) {
        sharedEditor.putString("login.autologin", "false");
        sharedEditor.putString("login.user", "");
        sharedEditor.putString("login.pw", "");
        sharedEditor.commit();
    }




}
