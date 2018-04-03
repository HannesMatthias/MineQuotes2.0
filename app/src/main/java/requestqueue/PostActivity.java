package requestqueue;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rubrunghi.dev.minequotes.R;
import org.json.JSONObject;
import java.io.IOException;import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class PostActivity extends Fragment {

    String url = "http://www.mhteam.bz.it:8080/HttpServlet/HttpServlet";
    HttpClient client;
    HttpResponse response;
    Button postMessage;
    TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.postlayout);
        message = (TextView) view.findViewById(R.id.getanswer);
        postMessage = (Button) view.findViewById(R.id.postJson);
        postMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToServer();

            }


        });

        return view;
    }

    public void sendDataToServer() {

        final String json = formatDataToJson();

        new AsyncTask<Void, Void,String>() {

            @Override
            protected String doInBackground(Void... params) {

                return getServerResponse(json);
            }

            @Override
            protected void onPostExecute(String result) {
                message.setText(result);
            }
        }.execute();



    }

    private String getServerResponse(String json) {

        HttpPost post = new HttpPost(url);

        try {
            StringEntity entity = new StringEntity(json);


           // post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json; charset=UTF-8");
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
    public String formatDataToJson() {
        JSONObject mylogin = new JSONObject();

        try {
            mylogin.put("username", "Hannes");
            mylogin.put("password", "123");
            return mylogin.toString();
        }catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }



}


