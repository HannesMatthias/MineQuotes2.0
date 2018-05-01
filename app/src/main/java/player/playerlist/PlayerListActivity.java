package player.playerlist;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.rubrunghi.dev.minequotes.Client;
import player.PlayerProfileActivity;


public class PlayerListActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private EditText searchField;
    private ProgressBar progressBar;
    private PlayerAdapter playerAdapter;
    private ListView playerlist;
    private Client client;
    private SwipeRefreshLayout refresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playerlist, container, false);
        super.onCreate(savedInstanceState);

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.list_items);
        refresh.setOnRefreshListener(this);

        searchField = (EditText) view.findViewById(R.id.searchBox);
        progressBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        client = new Client();
        playerlist = (ListView) view.findViewById(R.id.listView);
        setupListListener();
        ArrayList<Player> players = new ArrayList<>();
        playerAdapter = new PlayerAdapter(getActivity(), players);

        playerlist.setAdapter(playerAdapter);
        fetchPlayerData("playerdata");

        return view;
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(false);
        fetchPlayerData("playerdata");
    }

    private void fetchPlayerData(String action) {
        progressBar.setIndeterminate(true);
        client.getUrl(action, new JsonHttpResponseHandler() {
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

                       ArrayList<Player> filteredPlayers = LoadAllPlayersManager.filterPlayers(s.toString().toLowerCase());

                       loadList(filteredPlayers);
                       playerlist.post(new Runnable() {
                           @Override
                           public void run() {
                               playerlist.smoothScrollToPosition(0);
                           }
                       });

                if(isEmpty(searchField)) {

                    refreshList();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        playerlist.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ?
                        0 : playerlist.getChildAt(0).getTop();
                refresh.setEnabled((topRowVerticalPosition >= 0));
            }
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
