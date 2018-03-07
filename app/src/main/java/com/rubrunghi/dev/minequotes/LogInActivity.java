package com.rubrunghi.dev.minequotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by Admin_David on 28.02.2018.
 */

abstract class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedValues) {
        super.onCreate(savedValues);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        Button login = (Button) findViewById(R.id.logIn_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        Button signup = (Button) findViewById(R.id.signUp_btn);
        signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
        });
        return true;
    }
}
