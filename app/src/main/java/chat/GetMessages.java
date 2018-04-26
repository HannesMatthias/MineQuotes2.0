package chat;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.rubrunghi.dev.minequotes.Client;
import com.rubrunghi.dev.minequotes.MainActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import login.LoginActivity;


public class GetMessages extends AsyncTask<String, Void, String> {

    public AsyncResponse messageTaskResponse = null;
    ChatActivity activity;

    public GetMessages(ChatActivity activity) {
        this.activity = activity;
        messageTaskResponse = activity;
        Toast.makeText(activity.getActivity(), "NeuIn", Toast.LENGTH_SHORT).show();
        String json = formatMessageToJson();
        executeOnExecutor(ChatActivity.executor, json);
    }


    public interface AsyncResponse {
        void getMessageResponse(String output);
    }


    @Override
    protected String doInBackground(String... json) {
        return getMessageFromServer(json[0]);

    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("MessageList", result);
        messageTaskResponse.getMessageResponse(result);

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

    public String formatMessageToJson() {
        JSONObject chatMessage = new JSONObject();

        try {
            chatMessage.put("post", "chat_getMessages");
            chatMessage.put("senderID", MainActivity.uniquePlayerID);
            chatMessage.put("empfaengerID", activity.getEmpfängerID());
            return chatMessage.toString();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

}
