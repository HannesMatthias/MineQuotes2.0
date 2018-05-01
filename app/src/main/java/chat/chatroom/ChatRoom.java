package chat.chatroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.ProgressBar;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.rubrunghi.dev.minequotes.Client;
import com.rubrunghi.dev.minequotes.MainActivity;
import com.rubrunghi.dev.minequotes.R;

import org.json.JSONArray;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import chat.ChatActivity;
import chat.friends.Friends;
import cz.msebera.android.httpclient.Header;
import player.LoadAllPlayersManager;
import player.Player;
import player.PlayerProfileActivity;


public class ChatRoom extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static HashMap<String, ChatActivity> chats;

    private ChatRoomAdapter adapter;
    private ListView list;
    private ProgressBar progressBar;
    Client client;
    SwipeRefreshLayout layout;
    public ChatRoom() {
        chats = new HashMap<>();
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.playerlist, container, false);
        layout = (SwipeRefreshLayout) view.findViewById(R.id.list_items);
        layout.setOnRefreshListener(this);

        client = new Client();

        progressBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        list = (ListView) view.findViewById(R.id.listView);
        adapter = new ChatRoomAdapter(getActivity());
        list.setAdapter(adapter);

        fetchFriends();
        setupListListener();
        return view;
    }
    @Override
    public void onRefresh() {
        fetchFriends();
        layout.setRefreshing(false);

    }
    private void fetchFriends() {
        progressBar.setIndeterminate(true);
        client.getfriendsURL(MainActivity.uniquePlayerID, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if (response != null) {

                    ArrayList<Friends> players = Friends.fetchJsonArray(response);
                    Log.e("FetchFriends", players.size() + "");

                    adapter.clear();
                    Friends.friendsList.clear();
                    for (Friends f : players) {
                        adapter.add(f);
                        Friends.friendsList.add(f);
                    }

                    adapter.notifyDataSetChanged();
                    progressBar.setIndeterminate(false);


                }
            }


        });
    }


    public void setupListListener() {

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View vuew, int position, long l) {
                Friends player = (Friends) parent.getItemAtPosition(position);
                ChatActivity chat = ChatRoom.getChats().get(player.getFriendID()) != null ?  ChatRoom.getChats().get(player.getFriendID()) : null;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if(chat != null) {
                    fm.beginTransaction().addToBackStack("").replace(R.id.main, chat).commit();
                }else {
                    ChatActivity c = new ChatActivity();
                    Bundle b = new Bundle();
                    b.putString("empfaenger", player.getFriendID());
                    b.putString("name", player.getFriendName());
                    c.setArguments(b);

                    ChatRoom.setChats(player.getFriendID(), c);
                    fm.beginTransaction().addToBackStack("").replace(R.id.main, c).commit();
                }
            }
        });


        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ?
                        0 : list.getChildAt(0).getTop();
                list.setEnabled((topRowVerticalPosition >= 0));
            }
        });
    }

    public static HashMap<String, ChatActivity> getChats() {
        return chats;
    }

    public static void setChats(String uuid, ChatActivity chat) {
        chats.put(uuid, chat);
    }
}
