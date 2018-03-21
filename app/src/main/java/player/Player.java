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
    private final String defaultSkinHeadUrl = "https://crafatar.com/renders/head/";
    private final String defaultSkinBodyUrl = "https://crafatar.com/renders/body/";
    public String getPlayername() {
        return playername;
    }



    public void setSkinUrl(String url) {
        this.skinUrlHead = defaultSkinHeadUrl + url;
        this.skinUrlBody = defaultSkinBodyUrl + url;
       // Log.e("Skin", skinUrl);
    }

    public static Player getJsonObject(JSONObject o) {
        Player player = new Player();
        try {

            //##################PlayerList [Start]##################
            if (o.has("playername")) {
                player.playername = o.getString("playername");
               // Log.e("Spieler", player.playername);
            }
            if (o.has("playtime")) {
                player.playtime = o.getString("playtime");
            }
            if(o.has("uuid")) {
                player.setSkinUrl(o.getString("uuid"));
                player.uuid = o.getString("uuid");
            }
            if(o.has("lastOnlineTimeDate")) {
                player.lastOnlineDateTime = o.getString("lastOnlineTimeDate");
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
}
