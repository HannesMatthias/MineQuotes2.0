package login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.rubrunghi.dev.minequotes.R;

import java.util.ArrayList;

/**
 * Created by Admin_David on 21.03.2018.
 */

public class LoginActivity extends Fragment implements View.OnClickListener {


    private EditText user;
    private EditText passwd;
    private Button btn_login;
    LoginHandler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.login);

        user = (EditText) view.findViewById(R.id.user_field);
        passwd = (EditText) view.findViewById(R.id.passwd_field);
        btn_login = (Button) view.findViewById(R.id.logIn_btn);

        btn_login.setOnClickListener(this);
        handler = new LoginHandler();
        handler.getLogins("login");


        return view;
    }

    @Override
    public void onClick(View v) {
        if (btn_login == v){

           loginCheck();
        }
    }

    public void loginCheck() {
        ArrayList<LoginProfiles> profiles = handler.getLoginDatas();

        for (LoginProfiles profile : profiles){
            if (profile.getUsername().equals(user.getText().toString()) && profile.getPassword().equals(passwd.getText().toString())){
                Toast.makeText(getActivity(), "Eingeloggt ! ! !", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Toast.makeText(getActivity(), "einlogging gefailed", Toast.LENGTH_LONG).show();
    }

}
