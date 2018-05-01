package chat.friends;

import com.rubrunghi.dev.minequotes.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import player.Player;
import rank.RankPlayer;

/**
 * Created by Manfred on 30.04.2018.
 */

public class Friends {

    private String senderID;
    private String friendID;
    private String friendName;
    private final String DEFAULTSKINURL_HEAD = "https://crafatar.com/renders/head/";

    public Friends() {

    }
    public Friends(String senderID, String friendID, String friendName) {
        this.senderID = senderID;
        this.friendID = friendID;
        this.friendName = friendName;
    }

    public String getHeadUrl() {
        return DEFAULTSKINURL_HEAD + friendID;
    }

    public static ArrayList<Friends> friendsList = new ArrayList<>();



    public static Friends getJsonObject(JSONObject o) {
        Friends player = new Friends();
        try {

            //##################PlayerList [Start]##################

            if(o.has("senderID")) {
                player.setSenderID(o.getString("senderID"));
            }
            if (o.has("friendID")) {
                player.setFriendID(o.getString("friendID"));
            }
            if (o.has("friendName")) {
                player.setFriendName(o.getString("friendName"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return player;
    }

    public static ArrayList<Friends> fetchJsonArray(JSONArray a) {
        ArrayList<Friends> list = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            try {
                list.add(getJsonObject(a.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public String getFriendID() {
        return friendID;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public static void setFriendsList(ArrayList<Friends> friendsList) {
        Friends.friendsList = friendsList;
    }


}

