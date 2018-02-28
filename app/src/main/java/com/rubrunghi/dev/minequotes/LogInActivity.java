package com.rubrunghi.dev.minequotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Admin_David on 28.02.2018.
 */

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    Button login;
    protected void onCreate(Bundle savedValues) {
        login = (Button) findViewById(R.id.logIn_btn);
    }

    @Override
    public void onClick(View v) {

    }
}
