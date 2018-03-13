package player;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class PlayerClient {

    private final String BASIC_URL = "http://178.238.238.85:8080/HttpServlet/HttpServlet?name=";
    private AsyncHttpClient client;

    public PlayerClient() {
        client = new AsyncHttpClient();
    }



    public void getPlayers(String action, JsonHttpResponseHandler handler) {

        client.get(BASIC_URL + action, handler);
    }
}
