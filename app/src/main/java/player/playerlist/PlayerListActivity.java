package player.playerlist;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rubrunghi.dev.minequotes.R;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import player.LoadAllPlayersManager;
import player.Player;
import player.PlayerClient;
import player.PlayerProfileActivity;

/**
 * Created by Administrator on 28.02.2018.
 */

public class PlayerListActivity extends Fragment {

    private EditText searchField;
    private ProgressBar progressBar;
    private PlayerAdapter playerAdapter;
    private ListView playerlist;
    private PlayerClient playerClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playerlist, container, false);
        super.onCreate(savedInstanceState);
        searchField = (EditText) view.findViewById(R.id.searchPlayer);
        progressBar = (ProgressBar) view.findViewById(R.id.playerListLoadingBar);
        playerClient = new PlayerClient();
        playerlist = (ListView) view.findViewById(R.id.playerlistView);
        setupListListener();
        ArrayList<Player> players = new ArrayList<>();
        playerAdapter = new PlayerAdapter(getActivity(), players);

        playerlist.setAdapter(playerAdapter);
        fetchPlayerData("playerdata");

        return view;
    }



    private void fetchPlayerData(String action) {
        progressBar.setIndeterminate(true);
        playerClient.getPlayers(action, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if (response != null) {

                    ArrayList<Player> players = Player.fetchJsonArray(response);
                    Log.e("PLayerFetchingSize", players.size() +"");

                    playerAdapter.clear();
                    LoadAllPlayersManager.players.clear();
                    for (Player b : players) {
                        playerAdapter.add(b);
                        LoadAllPlayersManager.addPlayer(b);
                    }
                    playerAdapter.notifyDataSetChanged();
                    progressBar.setIndeterminate(false);

              //      Log.e("1","" + LoadAllPlayersManager.players.size());
                }
            }


        });



    }

    public void setupListListener() {

        playerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View vuew, int position, long l) {
                Player player = (Player) parent.getItemAtPosition(position);
                for (Player profiles : LoadAllPlayersManager.players) {
                    if(player.getPlayername().equals(profiles.getPlayername())) {

                        PlayerProfileActivity profileActivity = new PlayerProfileActivity();
                        Bundle b = new Bundle();
                        b.putString("uuid", player.getUuid());

                        profileActivity.setArguments(b);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().addToBackStack("").replace(R.id.main, profileActivity).commit();
                        break;
                    }
                }
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            //    for(Player profiles : LoadAllPlayersManager.players) {
             //      if(profiles.getPlayername().contains(s.toString())) {
                       /*
                       playerAdapter.clear();
                       playerAdapter.add(profiles);
                       playerAdapter.notifyDataSetChanged();
*/
                       ArrayList<Player> filteredPlayers = LoadAllPlayersManager.filterPlayers(s.toString().toLowerCase());

                       loadList(filteredPlayers);
                       playerlist.post(new Runnable() {
                           @Override
                           public void run() {
                               playerlist.smoothScrollToPosition(0);
                           }
                       });

                   //    break;
                 //  }

               // }
                if(isEmpty(searchField)) {

                    refreshList();
                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void refreshList() {
        playerAdapter.clear();
        for(Player p: LoadAllPlayersManager.players) {
            playerAdapter.add(p);
        }

        playerAdapter.notifyDataSetChanged();
    }
    public void loadList(ArrayList<Player> filterPlayerList) {
        playerAdapter.clear();
        for(Player p : filterPlayerList) {
            playerAdapter.add(p);
        }



        playerAdapter.notifyDataSetChanged();
    }

    public boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

}
