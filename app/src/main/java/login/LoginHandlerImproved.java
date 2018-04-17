package login;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rubrunghi.dev.minequotes.Client;
import com.rubrunghi.dev.minequotes.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;


public class LoginHandlerImproved {
    String url = "http://www.mhteam.bz.it:8080/HttpServlet/HttpServlet";
    private LoginProfiles profile;
    LoginActivityImproved fragment;
    public LoginHandlerImproved(LoginActivityImproved fragment) {
        this.fragment = fragment;
    }

    public void sendDataToServer(String username, String password) {

        final String json = formatDataToJson(username, password);


        new AsyncTask<Void, Void,String >() {


            @Override
            protected String doInBackground(Void... params) {


                return getServerResponse(json);
            }

            @Override
            protected void onPostExecute(String result) {
                Log.e("result", result);
                try {
                    profile = LoginProfiles.getJsonObject(new JSONObject(result));
                    if(profile != null) {
                        einloggen();
                    }else {
                        Toast.makeText(fragment, "Logindaten sind falsch", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(fragment, "Logindaten sind falsch", Toast.LENGTH_SHORT).show();
                    Log.e("Catch",e.getMessage());
                }


            }

        }.execute();



    }
    public void einloggen() {
      //  Toast.makeText(fragment, "Einloggen", Toast.LENGTH_LONG).show();
        Intent i = new Intent(fragment, MainActivity.class);
        LoginActivity.loggedInUser = profile;
        fragment.startActivity(i);
    }

    private String getServerResponse(String json) {

        HttpPost post = new HttpPost(url);

        try {
            StringEntity entity = new StringEntity(json);


            // post.setHeader("Content-type", "application/json");
            post.setHeader("Content-type", "application/json; charset=UTF-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(entity);
            //To send the data
            DefaultHttpClient client = new DefaultHttpClient();
            BasicResponseHandler handler = new BasicResponseHandler();

            try {
                String response = client.execute(post, handler);
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "Error, Server antwortet nicht...";
    }
    public String formatDataToJson(String username, String password) {
        JSONObject mylogin = new JSONObject();

        try {
            mylogin.put("username", username);
            mylogin.put("password", password);
            return mylogin.toString();
        }catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public LoginProfiles getProfile() {
        return profile;
    }
}
