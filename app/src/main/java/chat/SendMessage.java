package chat;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.rubrunghi.dev.minequotes.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadPoolExecutor;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;




public class SendMessage extends AsyncTask<String, Void, String>{

    public AsyncResponse asyncResponse = null;

    public SendMessage(ChatActivity activity, Message message) {
        asyncResponse = activity;
        String json = formatMessageToJson(message);
        Log.e("sendmessage", "vor exec");
        executeOnExecutor(ChatActivity.executor, json);


    }


    public interface AsyncResponse {
        void getSendResponse(String output);
    }


    @Override
    protected String doInBackground(String... json) {
        Log.e("toSend", json[0]);
        return getMessageFromServer(json[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        asyncResponse.getSendResponse(result);

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

    public String formatMessageToJson(Message message) {
        JSONObject chatMessage = new JSONObject();

        try {
            chatMessage.put("post", "chat_send");
            chatMessage.put("senderID", message.getSenderID() );
            chatMessage.put("empfaengerID", message.getEmpfängerID());
            chatMessage.put("message", message.getMessageText());
            return chatMessage.toString();
        }catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

}
