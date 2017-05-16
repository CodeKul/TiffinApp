package com.codekul.http;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Created by aniruddha on 12/5/17.
 */

public class App extends Application {

    private RequestQueue queue;
    private Gson gson;

    public RequestQueue q() {
        return queue == null ? queue = Volley.newRequestQueue(this) : queue;
    }

    public Gson gson() {
        return gson == null ? gson = new Gson() : gson;
    }
}
