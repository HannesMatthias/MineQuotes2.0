package player;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Administrator on 07.03.2018.
 */

public class LoadAllPlayersManager {

    public static ArrayList<Player> players = new ArrayList<>();


    public static void addPlayer(Player p) {
        if(!players.equals(p)) {
           players.add(p);
        }
    }
    public static void addPlayer(Player p, int pos) {
        if(!players.equals(p)) {
            players.add(pos, p);
        }
    }

    public static ArrayList<Player> filterPlayers(Player player) { //Benötige ich vlt. trotzdem nicht mehr.
        ArrayList<Player> filterList = new ArrayList<>();
        for(Player p : players) {
            if(p.getPlayername().contains(player.getPlayername())) {
                filterList.add(p);

            }
        }
        return filterList;
    }


}
