package com.rubrunghi.dev.minequotes;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Client {

    private final String BASIC_URL = "http://www.mhteam.bz.it:8080/HttpServlet/HttpServlet?name=";
    public static final String BASIC_URL_POST = "http://www.mhteam.bz.it:8080/HttpServlet/HttpServlet";
    //private final String BASIC_URL = "http://178.238.238.85:8080/HttpServlet/HttpServlet?name=";
    private AsyncHttpClient client;

    public Client() {
        client = new AsyncHttpClient();
    }


    public void getUrl(String action, JsonHttpResponseHandler handler) {

        client.get(BASIC_URL + action, handler);
    }

}
