package rank;

import android.util.Log;


import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rubrunghi.dev.minequotes.Client;
import com.rubrunghi.dev.minequotes.MainActivity;

import org.json.JSONArray;

import java.util.ArrayList;



public class RankHandler {
    ArrayList<RankPlayer> rankDatas;
    Client client;

    public RankHandler() {
        this.client = new Client();
        rankDatas = new ArrayList<>();

    }
    public void getRanks(String action) {

        client.getUrl(action, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                if (response != null) {
                    rankDatas = RankPlayer.fetchJsonArray(response);
                    Log.e("RankList loaded",String.valueOf(rankDatas.size()) );
                }
            }


        });
    }

    public ArrayList<RankPlayer> getRanks() {
        return rankDatas;
    }


}
