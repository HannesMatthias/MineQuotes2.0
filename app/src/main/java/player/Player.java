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
        Log.e("Skin", skinUrl);
    }

    public static Player formJSON(JSONObject o) {
        Player player = new Player();
        try {
            // Cover-IDhttps://crafatar.com/renders/head/179d5bad-37cd-4125-a624-c4e744532469
            if (o.has("Spieler_Name")) {
                player.playername = o.getString("Spieler_Name");

            }
            if (o.has("PlayTime")) {
                player.playtime = o.getString("PlayTime");
            }

            if(o.has("UUID")) {
                player.setSkinUrl(o.getString("UUID"));
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
