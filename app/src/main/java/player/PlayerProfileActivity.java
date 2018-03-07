package player;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import com.rubrunghi.dev.minequotes.R;


/**
 * Created by Administrator on 28.02.2018.
 */

public class PlayerProfileActivity extends AppCompatActivity {


    private ImageView skin;
    private TextView playername;
    private TextView playtime_h;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        skin = (ImageView) findViewById(R.id.skin);
        playername = (TextView) findViewById(R.id.profilename);
        playtime_h = (TextView) findViewById(R.id.playtime_h);


    }





}