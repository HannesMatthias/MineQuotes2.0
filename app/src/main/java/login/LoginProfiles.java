package login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import player.Player;


public class LoginProfiles {

    private String username;
    private String password;
    private String rankname;
    private String playerID;



    public static LoginProfiles getJsonObject(JSONObject o) {
        LoginProfiles loginProfiles = new LoginProfiles();
        try {

            //##################PlayerList [Start]##################
            if (o.has("username")) {
                 loginProfiles.username = o.getString("username");
            }
            if (o.has("playerID")) {
                loginProfiles.playerID = o.getString("playerID");
            }
            if(o.has("rankname")) {
                loginProfiles.rankname = o.getString("rankname");
            }
            if(o.has("pw")) {
                loginProfiles.password = o.getString("pw");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return loginProfiles;
    }

    public static ArrayList<LoginProfiles> fetchJsonArray(JSONArray a) {
        ArrayList<LoginProfiles> list = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            try {
                list.add(getJsonObject(a.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }



    public String getPassword() {
        return password;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getRankname() {
        return rankname;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public void setRankname(String rankname) {
        this.rankname = rankname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}