package com.codekul.sensors;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by aniruddha on 7/6/17.
 */

public class SyncUtils {

    private RequestQueue que;
    private Context context;

    public SyncUtils(Context context) {
        this.context = context;
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

    public interface OnPostCallback {
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

    @Deprecated
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

    @Deprecated
    private void delete(OnDeleteCallback callback) {
        //delete row from sqlite
        callback.onDelete();
    }

    @Deprecated
    private void receive(final OnReceiveCallback callback) {
        get("", new OnGetCallback() {
            @Override
            public void onGet(String res) {
                callback.onReceive("");
            }
        });
    }

    @Deprecated
    public void performSync(OnSyncCallback syncCallback) {

        final List<JSONObject> objs = new ArrayList<>();
        for (JSONObject obj : objs) {
            upload(syncCallback, obj);
        }

        que.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<JSONObject>() {
            @Override
            public void onRequestFinished(Request<JSONObject> request) {
                if (que.getSequenceNumber() >= objs.size()) {
                    receive(new OnReceiveCallback() {
                        @Override
                        public void onReceive(String res) {

                        }
                    });
                }
            }
        });
    }
    public JSONObject postBlocking(String url, JSONObject obj) {
        JSONObject resObj = null;
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        que.add(new JsonObjectRequest(Request.Method.POST, url, obj, future, future));
        try {
            resObj = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resObj;
    }

    public String getBlocking(String url) throws InterruptedException, ExecutionException, TimeoutException {
        String res = "";

        RequestFuture<String> future = RequestFuture.newFuture();
        que.add(new StringRequest(url, future, future));

        res = future.get(30, TimeUnit.SECONDS);

        return res;
    }

    private void saveProfileAccount() {

        String url = "http://www.angga-ari.com/api/something/awesome";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                params.put("name", "");
                params.put("location", "");
                params.put("about", "");
                params.put("contact", "");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("avatar", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(context, R.mipmap.ic_launcher_round), "image/jpeg"));
                return params;
            }
        };
    }

    public JSONObject postMultiPart(final int i) throws ExecutionException, InterruptedException {

        JSONObject result = null;
        RequestFuture<NetworkResponse> future = RequestFuture.newFuture();

        String url = "http://192.168.2.50:8080/ekam/sync/RAM";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.PUT, url, future, future) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", "aniruddha@codekul.com");
                params.put("outletcode", "" + i);
                params.put("longitude", "72.58");
                params.put("latitude", "18.8956");
                params.put("updatetimestamp", "9887978675");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                //params.put("file", new DataPart(i+".png", AppHelper.getFileDataFromDrawable(context, R.mipmap.ic_launcher_round), "image/jpeg"));
                try {
                    params.put("file", new DataPart(i + ".png", AppHelper.fileBytes(new File("")), "image/jpeg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        que.add(multipartRequest);

        NetworkResponse response = future.get();
        if (response.statusCode == 200) {
            String resultResponse = new String(response.data);
            try {
                result = new JSONObject(resultResponse);
                String status = result.getString("Status");
                Log.i("@codekul", "Status is " + status);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
