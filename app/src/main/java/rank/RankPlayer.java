package rank;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RankPlayer {

    private String uuid;
    private String playername;

    private int punkte;
    private int rang;
    private double kd;
    private String skinUrlHead;

    private final String DEFAULTSKINURL_HEAD = "https://crafatar.com/renders/head/";


    public static RankPlayer getJsonObject(JSONObject o) {
        RankPlayer rank = new RankPlayer();
        try {

            if(o.has("uuid")) {
                rank.uuid = o.getString("uuid");

                rank.setSkinUrl(rank.uuid, 6);
            }

            if (o.has("playername")) {
                rank.playername = o.getString("playername");

            }
            if(o.has("kd")) {
                rank.kd = o.getDouble("kd");
            }

            if(o.has("rank")) {
                rank.rang = o.getInt("rank");
            }
            if(o.has("punkte")) {
                rank.punkte = o.getInt("punkte");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rank;
    }

    public static ArrayList<RankPlayer> fetchJsonArray(JSONArray a) {
        ArrayList<RankPlayer> list = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            try {
                list.add(getJsonObject(a.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public String getPlayername() {
        return playername;
    }

    public void setSkinUrl(String uuid, int headsize) {
        this.skinUrlHead = DEFAULTSKINURL_HEAD + uuid + "?scale=" + headsize +"&overlay";
    }


    public double getKd() {
        return kd;
    }

    public int getPunkte() {
        return punkte;
    }

    public int getRang() {
        return rang;
    }

    public String getUuid() {
        return uuid;
    }

    public String getSkinUrlHead() {
        return skinUrlHead;
    }


}
