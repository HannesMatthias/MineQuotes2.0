package player;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Manfred on 28.02.2018.
 */

public class Player {

    private String uuid;
    private String playername;
    private String playtime;
    private String lastOnlineDateTime;

    private String skinUrlHead;
    private String skinUrlBody;
    private final String DEFAULTSKINURL_HEAD = "https://crafatar.com/renders/head/";
    private final String DEFAULTSKINURL_BODY = "https://crafatar.com/renders/body/";

    private boolean status;
    public String getPlayername() {
        return playername;
    }



    public void setSkinUrl(String url, int headsize, int bodysize) {
        this.skinUrlHead = DEFAULTSKINURL_HEAD + url + "?scale=" + headsize +"&overlay";
        this.skinUrlBody = DEFAULTSKINURL_BODY + url + "?scale=" + bodysize +"&overlay";
       // Log.e("Skin", skinUrl);
    }

    public static Player getJsonObject(JSONObject o) {
        Player player = new Player();
        try {

            //##################PlayerList [Start]##################

            if(o.has("uuid")) {
                player.uuid = o.getString("uuid");
                player.setSkinUrl(o.getString("uuid"),6,10);
            }

            if (o.has("playername")) {
                player.playername = o.getString("playername");
            }
            if (o.has("playtime")) {
                player.playtime = o.getString("playtime");
            }

            if(o.has("lastOnlineTimeDate")) {
                player.lastOnlineDateTime = o.getString("lastOnlineTimeDate");
            }
            if(o.has("status")) {
                player.status = o.getBoolean("status");
            }
            //##################PlayerList [End]##################


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return player;
    }

    public static ArrayList<Player> fetchJsonArray(JSONArray a) {
        ArrayList<Player> list = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            try {
                list.add(getJsonObject(a.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public String getPlaytime() {
        return playtime;
    }

    public String getUuid() {
        return uuid;
    }

    public String getSkinUrlHead() {
        return skinUrlHead;
    }

    public String getSkinUrlBody() {
        return skinUrlBody;
    }

    public String getLastOnlineDateTime() {
        return lastOnlineDateTime;
    }

    public boolean isStatus() {
        return status;
    }
}
