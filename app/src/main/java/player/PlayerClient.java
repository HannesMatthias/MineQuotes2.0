package player;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class PlayerClient {

  //  private static String API_BASE_URL = "http://178.238.238.85:7809/gk/playtime2.json";
    private static String API_BASE_URL = "http://178.238.238.85:8080/HttpServlet/HttpServlet?name=playerdata";
    private AsyncHttpClient client;

    public PlayerClient() {
        client = new AsyncHttpClient();
    }



    public void getPlayers(JsonHttpResponseHandler handler) {
        client.get(API_BASE_URL, handler);
    }
}
