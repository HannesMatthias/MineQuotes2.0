package player;


import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.rubrunghi.dev.minequotes.R;
import com.squareup.picasso.Picasso;


/**
 *
 * Created by Administrator on 28.02.2018.
 */

public class PlayerProfileActivity extends Fragment {

    Player p;
    private ImageView skin;
    private TextView playername;
    private TextView playtime_days;
    private TextView playtime_h;
    private TextView playtime_m;
    private TextView lastTimeOnline;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playerprofile, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.playerprofile);
        skin = (ImageView) view.findViewById(R.id.skin);
        playername = (TextView) view.findViewById(R.id.profilename);
        playtime_days = (TextView) view.findViewById(R.id.playtime_days);
        playtime_h = (TextView) view.findViewById(R.id.playtime_h);
        playtime_m = (TextView) view.findViewById(R.id.playtime_m);
        lastTimeOnline =  (TextView) view.findViewById(R.id.lastTimeOnline);
        searchProfile();
        Picasso.with(getContext()).load(Uri.parse(p.getSkinUrlBody())).error(R.mipmap.ic_launcher).into(skin);
        playername.setText(p.getPlayername());

        divideTime();

        return view;
    }

    public void searchProfile() {
        Bundle getDatas = getArguments();
        String uuid = getDatas.getString("uuid");
        this.p = LoadAllPlayersManager.getPlayerByUuid(uuid);
        Log.e("Player profile searcher", p.getPlayername());

    }
    public void divideTime() {
        int playtime = Integer.parseInt(p.getPlaytime());
        Log.e("Playtime", playtime + "");
        playtime /= 1000L;

        int days = (int) (playtime / 86400);
        playtime -= 86400 * days;

        int hours = (int) (playtime / 3600);
        playtime -= 3600 * hours;

        int minutes = (int) (playtime / 60);
        playtime -= 60 * minutes;


        StringBuilder sb = new StringBuilder();
        sb.append(days).append("day").append(days == 1 ? " " : "s");
        playtime_days.setText(sb);
        playtime_h.setText(String.valueOf(hours) + "h");
        playtime_m.setText(String.valueOf(minutes) + "min");

        lastTimeOnline.setText(p.getLastOnlineDateTime());


    }







}