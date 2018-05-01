package player;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rubrunghi.dev.minequotes.MainActivity;
import com.rubrunghi.dev.minequotes.R;
import com.squareup.picasso.Picasso;

import chat.ChatActivity;
import chat.chatroom.ChatRoom;

public class PlayerProfileActivity extends Fragment implements View.OnClickListener {

    Player p;
    private ImageView skin;
    private TextView playername;
    private TextView playtime_days;
    private TextView playtime_h;
    private TextView playtime_m;
    private TextView lastTimeOnline;
    private ImageView onlinestatus;
    private Button chatButton;



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
        onlinestatus = (ImageView) view.findViewById(R.id.onlineCheck);
        chatButton = (Button) view.findViewById(R.id.profile_chat);
        chatButton.setOnClickListener(this);
        searchProfile();
        Picasso.with(getContext()).load(Uri.parse(p.getSkinUrlBody())).error(R.mipmap.ic_launcher).into(skin);
        playername.setText(p.getPlayername());
        if(p.isStatus()) {
            onlinestatus.setImageResource(R.mipmap.online);
        }else {
            onlinestatus.setImageResource(R.mipmap.offline);
        }

        divideTime();

        return view;
    }

    @Override
    public void onClick(View view) {
        if(chatButton == view) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Bundle b = new Bundle();

            if(ChatRoom.getChats().get(p.getUuid()) != null) {
                fm.beginTransaction().addToBackStack("").replace(R.id.main, ChatRoom.getChats().get(p.getUuid())).commit();
                return;
            }
            ChatActivity chat = new ChatActivity();
            b.putString("empfaenger", p.getUuid());
            b.putString("name", playername.getText().toString());
            chat.setArguments(b);

            ChatRoom.setChats(playername.getText().toString(), chat);
            fm.beginTransaction().addToBackStack("").replace(R.id.main, chat).commit();



        }
    }

    public void searchProfile() {
        Bundle getDatas = getArguments();
        String uuid = getDatas.getString("uuid");
        this.p = LoadAllPlayersManager.getPlayerByUuid(uuid);


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