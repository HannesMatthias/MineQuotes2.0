package login;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rubrunghi.dev.minequotes.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import rank.RankActivity;


public class LoginHandler {
    ArrayList<LoginProfiles> loginDatas;
    Client client;

    public LoginHandler() {
        this.client = new Client();
        loginDatas = new ArrayList<>();

    }

    public void getLogins(String action) {

        client.getUrl(action, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if (response != null) {
                    Log.e("Login","logging");
                    loginDatas = LoginProfiles.fetchJsonArray(response);

                }
            }


        });



    }

    public ArrayList<LoginProfiles> getLoginDatas() {
        return loginDatas;
    }
}
