package login;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.rubrunghi.dev.minequotes.R;

import java.util.ArrayList;


public class LoginActivityImproved extends AppCompatActivity implements View.OnClickListener {


    private EditText user;
    private EditText passwd;
    private Button btn_login;
    LoginHandlerImproved handler;

    ArrayList<LoginProfiles> arrPlayer = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        user = (EditText) findViewById(R.id.user_field);
        passwd = (EditText) findViewById(R.id.passwd_field);
        btn_login = (Button) findViewById(R.id.logIn_btn);

        btn_login.setOnClickListener(this);
        handler = new LoginHandlerImproved(this);
    }

    @Override
    public void onClick(View v) {
        if (btn_login == v){
           loginCheck();
        }
    }

    public static LoginProfiles loggedInUser;

    public void loginCheck() {
        String username = user.getText().toString();
        String pw = passwd.getText().toString();
//        String username = "Hannes";
//        String pw = "123";
        handler.sendDataToServer(username, pw);

    }





}
