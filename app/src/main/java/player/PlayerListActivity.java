package player;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rubrunghi.dev.minequotes.MainActivity;
import com.rubrunghi.dev.minequotes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 28.02.2018.
 */

public class PlayerListActivity extends Fragment implements AdapterView.OnItemSelectedListener{


    private PlayerAdapter playerAdapter;
    private ListView playerlist;
    private PlayerClient playerClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playerlist, container, false);
        super.onCreate(savedInstanceState);

        playerClient = new PlayerClient();
        playerlist = (ListView) view.findViewById(R.id.playerlistView);
        ArrayList<Player> players = new ArrayList<>();
        playerAdapter = new PlayerAdapter(getActivity(), players);

        playerlist.setAdapter(playerAdapter);
        fetchPlayerData();

        return view;
    }



    private void fetchPlayerData() {
        playerClient.getBooks(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if (response != null) {
                    ArrayList<Player> players = Player.fromJSON(response);
                    playerAdapter.clear();
                    for (Player b : players) {
                        playerAdapter.add(b);
                    }
                    playerAdapter.notifyDataSetChanged();

                }
            }


        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String playername = (String) adapterView.getItemAtPosition(i);
        for (Player profiles : LoadAllPlayersManager.players) {
            if(playername.equals(profiles.getPlayername())) {
                Intent intent = new Intent(this.getContext(), PlayerProfileActivity.class );
               // intent.putExtra("Player", new )

                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
