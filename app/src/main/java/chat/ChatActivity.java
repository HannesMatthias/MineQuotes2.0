package chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rubrunghi.dev.minequotes.Client;
import com.rubrunghi.dev.minequotes.MainActivity;
import com.rubrunghi.dev.minequotes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chat.adapter.ChatAdapter;
import cz.msebera.android.httpclient.Header;
import player.LoadAllPlayersManager;
import player.Player;


public class ChatActivity extends Fragment implements SendMessage.AsyncResponse, GetMessages.AsyncResponse, ListenMessages.AsyncResponse, View.OnClickListener {

    ListenMessages listenMessages;
    SendMessage handler;
    GetMessages getMessagesTask;
    ChatAdapter adapter;
    FloatingActionButton send;
    FloatingActionButton addUser;
    RelativeLayout layout;
    EditText input;
    View view;
    ListView chatView;
    private String empfängerID;
    private String empfänger;
    public static ExecutorService executor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);
        layout = (RelativeLayout) view.findViewById(R.id.chatActivity);

        executor = Executors.newFixedThreadPool(99999);

        adapter = new ChatAdapter(getContext()); //Wenns nicht geht, dann Constructor ändern!
        send = (FloatingActionButton) view.findViewById(R.id.send);
        addUser = (FloatingActionButton) view.findViewById(R.id.addFriend);
        send.setOnClickListener(this);
        addUser.setOnClickListener(this);
        input = view.findViewById(R.id.input);

        chatView = view.findViewById(R.id.messageList);
        Bundle i = getArguments();
        if(i.getString("empfaenger") != null) {
            empfängerID = i.getString("empfaenger");
        }else {
            Toast.makeText(getActivity(), "Empfänger wurde nicht gefunden!", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (i.getString("name") != null) {
            empfänger = i.getString("name");
        }
        getMessagesTask = new GetMessages(this);
        chatView.setAdapter(adapter);

        return view;
    }


    @Override
    public void getSendResponse(String output) {
        //Toast.makeText(getActivity(), "out " + output, Toast.LENGTH_SHORT).show();

        addMessagesToAdapter(output);
    }
    @Override
    public void getMessageResponse(String output) {
        Toast.makeText(getActivity(), "MessagesList " + output, Toast.LENGTH_SHORT).show();
        addMessagesToAdapter(output);

        if(listenMessages == null) {
            listenMessages = new ListenMessages(this);
        }
    }

    @Override
    public void foundNewMessages(String output) {

        if(output.contains("true")) {
           // Toast.makeText(getActivity(), "Neu!", Toast.LENGTH_SHORT).show();
            getMessagesTask = new GetMessages(this);

            listenMessages = new ListenMessages(this);
        }
    }

    @Override
    public void onClick(View view) {
        if(send == view) {
        //    Toast.makeText(getActivity(), "Sende Nachricht....", Toast.LENGTH_SHORT).show();
            Message message = new Message();
            message.setSenderID(MainActivity.uniquePlayerID);
            message.setMessageText(input.getText().toString());
            message.setEmpfängerID(empfängerID);

            handler = new SendMessage(this, message);
        }else if (addUser == view) {
            Client c = new Client();
            c.setfriendsURL(MainActivity.uniquePlayerID, empfängerID, empfänger, new JsonHttpResponseHandler());
            Toast.makeText(getActivity(), "Freund hinzugefügt", Toast.LENGTH_SHORT).show();
        }

    }
    ArrayList<Message> messages;
    public void addMessagesToAdapter(String output) {
        try {
            JSONArray outputArray = new JSONArray(output);

            messages = Message.fetchJsonArray(outputArray);
            Log.e("MessageSize", "" + messages.size());

            adapter.clear();

            for (Message m : messages) {
                adapter.add(m);
            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public boolean getUserVisibleHint() {

        if(!getUserVisibleHint()) {
            executor.shutdown();
            Log.e("Thread", "Offline");
        }
        return super.getUserVisibleHint();

    }

    public String getEmpfängerID() {
        return empfängerID;
    }

    public String getNewestMessageID() {

        if(messages.size() == 0) {
            return "0";
        }
        return messages.get(messages.size()-1).getMessageID();
    }

}
