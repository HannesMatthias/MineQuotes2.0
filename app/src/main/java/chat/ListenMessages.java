package chat;

import android.os.AsyncTask;
import android.util.Log;

import com.rubrunghi.dev.minequotes.Client;
import com.rubrunghi.dev.minequotes.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class ListenMessages extends AsyncTask<String, Void, String> {

    public AsyncResponse listenMessageTask = null;
    ChatActivity activity;

    public ListenMessages(ChatActivity activity) {
        this.activity = activity;
        listenMessageTask = activity;
        String json = askMessageToJson();

        Log.e("JsonListen", json);
        executeOnExecutor(ChatActivity.executor, json);
    }


    public interface AsyncResponse {
        void foundNewMessages(String output);
    }


    @Override
    protected String doInBackground(String... json) {
        String result = "";
        boolean listening = true;


        while (listening) {
            String message = json[0];
            Log.e("askChatJson", message);
            result = getMessageFromServer(message);
            Log.e("resultChat", result);
            if(result.contains("true")) {
                Log.e("TRUE", result);
                listening = false;
            }

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        listenMessageTask.foundNewMessages(result);

    }

    private String getMessageFromServer(String json) {

        HttpPost post = new HttpPost(Client.BASIC_URL_POST);

        try {
            StringEntity entity = new StringEntity(json);


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

            return "Something went wrong :/";
        }

        return null;
    }

    public String askMessageToJson() {
        JSONObject askMessage = new JSONObject();
        Log.e("JsonListen0", " " + activity.getNewestMessageID());
        try {
            askMessage.put("post", "chat_askMessages");
            askMessage.put("senderID", MainActivity.uniquePlayerID);
            askMessage.put("empfaengerID", activity.getEmpfängerID());
            askMessage.put("lastMessage", Integer.parseInt(activity.getNewestMessageID()));

            return askMessage.toString();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

}
