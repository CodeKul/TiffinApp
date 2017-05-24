package com.codekul.http;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codekul.http.domain.ExWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1, 0.1f, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                /*
                * - StringRequest - get string,  convert it to pojo,  using Gson
                * - JSONObjectRequest - post the json object
                * - JsonRequest - get the plain json
                * - Request
                * */
                ((App) getApplication()).q().add(new StringRequest(
                        "http://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&mode=json&units=metric&cnt=7&appid=7406c21bb1cb9f59d936a59c4e890279",
                        res -> onResponse(res, location),
                        MainActivity.this::onError
                ));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private void onResponse(String json, Location location) {
        Log.i("@codekul", json);

        /*
            - http://www.jsonschema2pojo.org/
            web app ussed to convert json to pojo
        */

        ExWeather weather = ((App) getApplication()).gson().fromJson(json, ExWeather.class);
        ((TextView) findViewById(R.id.textInfo)).setText(weather.getWeather().get(0).getDescription() +" lat - "+location.getLatitude() +" lng - "+location.getLongitude());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lat", location.getLatitude());
            jsonObject.put("lng", location.getLongitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i = 10;
        ((App) getApplication()).q().add(
                new JsonObjectRequest("https://digital-shelter-153912.firebaseio.com/weather.json",
                        jsonObject,
                        res -> {

                        },
                        err ->{})
        );
    }

    private void onError(VolleyError err) {
        Log.i("@codekul", "" + err.toString());
    }


    private void sendImageWithData() {

        // https://gist.github.com/anggadarkprince/a7c536da091f4b26bb4abf2f92926594
        VolleyMultipartRequest request =
                new VolleyMultipartRequest(Request.Method.POST ,"url", res ->{}, err->{}){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> reqParams = new HashMap<>();
                        reqParams.put("imei", "avadffads");

                        return reqParams;
                    }

                    @Override
                    protected Map<String, DataPart> getByteData() throws AuthFailureError {
                        Map<String, DataPart> map = new HashMap<>();
                        DataPart part = new DataPart();
                        part.setContent("alkfjlajdffla".getBytes());
                        part.setFileName("android");
                        part.setType("image");
                        map.put("key", part);
                        return map;
                    }
                };

        ((App)getApplication()).q().add(request);
    }

    public void oauth(View view) {
        ((App)getApplication()).q().add(
                new StringRequest(Request.Method.POST,"http://ec2-54-68-167-166.us-west-2.compute.amazonaws.com:9001/authorizationserver/oauth/token", res -> {

                    Log.i("@codekul",""+res);
                }, err->{
                    Log.i("@codekul",""+err.getMessage());
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        String creds = String.format("%s:%s","melayer","melayer");
                        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                        params.put("Authorization", auth);
                        params.put("Accept","application/json");
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        Log.i("@codekul", "Auth - "+auth);
                        return params;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("grant_type","client_credentials");
                        return params;
                    }
                }
        );
    }
}
