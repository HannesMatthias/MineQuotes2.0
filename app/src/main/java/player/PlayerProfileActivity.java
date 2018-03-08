package player;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rubrunghi.dev.minequotes.R;


/**
 * Created by Administrator on 28.02.2018.
 */

public class PlayerProfileActivity extends Fragment {


    private ImageView skin;
    private TextView playername;
    private TextView playtime_h;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playerprofile, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.playerprofile);
        skin = (ImageView) view.findViewById(R.id.skin);
        playername = (TextView) view.findViewById(R.id.profilename);
        playtime_h = (TextView) view.findViewById(R.id.playtime_h);

/*
        time /= 1000L;
        int days = (int) (time / 86400L);
        time -= 86400 * days;
        int hours = (int) (time / 3600L);
        time -= 3600 * hours;
        // int minutes = (int) (time / 60L);
        // time -= 60 * minutes;
        // int seconds = (int) time;

        StringBuilder sb = new StringBuilder();
		if (days != 0) {
			sb.append(days).append("Tag").append(days == 1 ? " " : "e ");

		}

        */

        return view;
    }





}