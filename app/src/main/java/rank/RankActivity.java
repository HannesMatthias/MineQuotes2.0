package rank;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rubrunghi.dev.minequotes.MainActivity;
import com.rubrunghi.dev.minequotes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RankActivity extends Fragment {

    RankAdapter adapter;
    RankPlayer rankPlayer;
    private ImageView skinHead;
    private TextView playername;
    private TextView points;
    private TextView kd;
    private TextView rank;
    private ListView ranklist;

    ArrayList<RankPlayer> arrPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rangliste, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.ranglisteRelativeLayout);


        skinHead = (ImageView) view.findViewById(R.id.skin);
        playername = (TextView) view.findViewById(R.id.profilename);
        points = (TextView) view.findViewById(R.id.punkte);
        kd = (TextView) view.findViewById(R.id.kd);
        rank = (TextView) view.findViewById(R.id.rang);
        ranklist = (ListView) view.findViewById(R.id.listView);


        arrPlayer = MainActivity.rankHandler.getRanks();
        getPlayerProfile();
        try {
            Picasso.with(getContext()).load(Uri.parse(rankPlayer.getSkinUrlHead())).error(R.mipmap.ic_launcher).into(skinHead);
        }catch (Exception e) {
            Log.e("Error_on_loading_image", "Bild konnte nicht geladen werden!");
        }
        double kd = Math.round(rankPlayer.getKd() * 10.0) / 10.0;
        playername.setText(rankPlayer.getPlayername());
        points.setText(String.valueOf(rankPlayer.getPunkte()));
        this.kd.setText(String.valueOf(kd));
        rank.setText("#" + String.valueOf(rankPlayer.getRang()));

        adapter = new RankAdapter(getActivity(), arrPlayer);
        ranklist.setAdapter(adapter);
        return view;




    }

    public void getPlayerProfile() {
        for(RankPlayer profiles : arrPlayer) {
            if(profiles.getUuid().equals(MainActivity.uniquePlayerID)) {
                rankPlayer = profiles;
                Log.e("Found Player", rankPlayer.getPlayername());
                break;
            }
        }
    }





}