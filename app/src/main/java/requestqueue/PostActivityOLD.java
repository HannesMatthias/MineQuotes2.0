package requestqueue;

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

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;


public class PostActivityOLD extends Fragment {

    String url = "http://www.mhteam.bz.it:8080/HttpServlet/";
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


                HttpParams httpsParameters = new BasicHttpParams();
                //Timeout max. setzen, falls User zu langsames Internet hat.
                int timeOutSocket = 20000;
                int timeoutConnection = 20000;

                HttpConnectionParams.setConnectionTimeout(httpsParameters, timeOutSocket);
                HttpConnectionParams.setSoTimeout(httpsParameters,timeoutConnection);

                client = new DefaultHttpClient(httpsParameters);

                try {
                    new PostJSON().execute("no values needed :)");
                }catch (Exception e) {
                    e.fillInStackTrace();
                }

            }
        });

        return view;
    }

    public void onPostExecute(String result) {
        if(result != null) {
            Log.e("POSTEEXE", "Result POSTED");
        }else {
            Log.e("POSTEXE", "Result NOT POSTED");
        }
    }

    public class PostJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                HttpPost post = new HttpPost(url.toString());

                List<NameValuePair> nameValuePair = new ArrayList<>();


                nameValuePair.add(new BasicNameValuePair("name", "test"));
                nameValuePair.add(new BasicNameValuePair("oname", "test2"));

                //Im Header festlegen, dass es sich um JSON handelt, sodass der Server weiß, was auf ihn zukommt.
                post.setHeader("Accept", "application/json");
                post.setHeader("Content-type", "application/json; charset=UTF-8");

                //UTF-8 Erkennung
                post.setEntity(new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8));

                //execute the post
                HttpResponse r = client.execute(post);

                //serverAntwort checken
                int status = r.getStatusLine().getStatusCode();

                //1XX info, 2xx success, 3xx redirect, 4xx client error, 5xx server error
                if(status == 200) {
                    Log.e("PostSuccess","Nice, u send a message :)");

                    InputStream inputStream = r.getEntity().getContent();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder stringBuilder = new StringBuilder();

                    String bufferedStrChunk = null;

                    while((bufferedStrChunk = bufferedReader.readLine()) != null){
                        stringBuilder.append(bufferedStrChunk);
                    }


                    message.setText(stringBuilder);





                    return "Success";
                }else {
                    Log.e("PostFailed","Failed to post the message to the server");
                    return null;
                }



            }catch (Exception e) {
                e.fillInStackTrace();
            }

            return null;
        }
    }
}


