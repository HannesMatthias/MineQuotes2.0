package chat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import login.LoginProfiles;
/*
 * Created by Manfred on 03.04.2018.
 */

public class Message {
    private String messageText;
    private String messageTime;
    private String senderID;
    private String empfängerID;
    private String messageID;
    public Message(String messageID, String messageText, String senderID, String empfängerID) {
        this.messageID = messageID;
        this.messageText = messageText;
        this.senderID = senderID;
        this.empfängerID = empfängerID;
    }

    public Message() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }


    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getEmpfängerID() {
        return empfängerID;
    }

    public void setEmpfängerID(String empfängerID) {
        this.empfängerID = empfängerID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public static Message getJsonObject(JSONObject o) {
        Message message = new Message();
        try {


            if (o.has("id")) {
                message.messageID = o.getString("id");
            }

            if (o.has("sender")) {
                message.senderID = o.getString("sender");
            }
            if (o.has("empfaenger")) {
                message.empfängerID = o.getString("empfaenger");
            }
            if(o.has("message")) {
                message.setMessageText( o.getString("message"));
            }
            if(o.has("timestamp")) {
                message.setMessageTime(o.getString("timestamp"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static ArrayList<Message> fetchJsonArray(JSONArray a) {
        ArrayList<Message> list = new ArrayList<>();

        for (int i = 0; i < a.length(); i++) {
            try {
                list.add(getJsonObject(a.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }







}