package player;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 28.02.2018.
 */

public class Player {


    private String playername;
    private String playtime;
    private String skinUrl;
    private final String defaultSkinHeadUrl = "https://crafatar.com/renders/head/";
    public String getPlayername() {
        return playername;
    }

    public String getPlaytime() {
        return playtime;
    }

    public String getSkinUrl() {
        return skinUrl;
    }

    public void setSkinUrl(String url) {
        this.skinUrl = defaultSkinHeadUrl + url;
       // Log.e("Skin", skinUrl);
    }

    public static Player formJSON(JSONObject o) {
        Player player = new Player();
        try {


            if (o.has("playername")) {
                player.playername = o.getString("playername");
               // Log.e("Spieler", player.playername);
            }
            if (o.has("playtime")) {
                player.playtime = o.getString("playtime");
            }

            if(o.has("uuid")) {
                player.setSkinUrl(o.getString("uuid"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return player;
    }

    public static ArrayList<Player> fromJSON(JSONArray a) {
        ArrayList<Player> list = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            try {
                list.add(formJSON(a.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
