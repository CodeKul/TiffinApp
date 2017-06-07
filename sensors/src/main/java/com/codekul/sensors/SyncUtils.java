package com.codekul.sensors;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniruddha on 7/6/17.
 */

public class SyncUtils {

    private RequestQueue que;

    public SyncUtils(Context context) {
        que = Volley.newRequestQueue(context);
    }

    public interface OnSyncCallback {
        void onSync();
    }

    public interface OnDeleteCallback {
        void onDelete();
    }

    public interface OnReceiveCallback {
        void onReceive(String res);
    }

    public interface OnPostCallback{
        void onPost(JSONObject obj);
    }

    public interface OnGetCallback {
        void onGet(String res);
    }

    private void post(String url, JSONObject obj, final OnPostCallback callback) {
        que.add(new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onPost(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    private void get(String url, final OnGetCallback callback) {

        que.add(new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onGet(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    private void upload(final OnSyncCallback callback, JSONObject obj) {
        post("", obj, new OnPostCallback() {
            @Override
            public void onPost(JSONObject obj) {
                delete(new OnDeleteCallback() {
                    @Override
                    public void onDelete() {

                    }
                });
            }
        });
    }

    private void delete(OnDeleteCallback callback) {
        //delete from sqlite
        callback.onDelete();
    }

    private void receive(final OnReceiveCallback callback) {
        get("", new OnGetCallback() {
            @Override
            public void onGet(String res) {
                callback.onReceive("");
            }
        });
    }

    public void performSync(OnSyncCallback syncCallback) {

        List<JSONObject> objs = new ArrayList<>();
        for (JSONObject obj : objs) {
            upload(syncCallback, obj);
        }

        receive(new OnReceiveCallback() {
            @Override
            public void onReceive(String res) {

            }
        });
    }
}
