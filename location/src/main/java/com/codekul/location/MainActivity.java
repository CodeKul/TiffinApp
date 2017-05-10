package com.codekul.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);


    }

    public void fetchData(View view) {
        //forward();
        reverse();
    }

    private void forward() {
        try {
            List<Address> addresses = ((App) getApplication()).geocoder().getFromLocation(18.72, 72.56, 5);
            for (Address address : addresses) {
                Log.i("@codekul", "Country - " + address.getCountryName());
                Log.i("@codekul", "Country Code - " + address.getCountryCode());
                Log.i("@codekul", "Address Line Code - " + address.getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reverse() {
        try {
            List<Address> addresses = ((App) getApplication()).geocoder().getFromLocationName("Codekul, Pune", 5);
            for (Address address : addresses) {
                Log.i("@codekul", "Country - " + address.getCountryName());
                Log.i("@codekul", "Country Code - " + address.getCountryCode());
                Log.i("@codekul", "Address Line Code - " + address.getAddressLine(0));
                Log.i("@codekul", "Lat - " + address.getLatitude());
                Log.i("@codekul", "Lng - " + address.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Criteria criteria() {

        Criteria criteria = new Criteria();
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);

        return criteria;
    }

    private void bestProvider() {
        String provider = manager.getBestProvider(criteria(), true);
        Log.i("@codekul", "Provider - " + provider);
        ((TextView) findViewById(R.id.textData)).setText("Provider is - " + provider);
    }

    private void myLocV1() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null)
            ((TextView) findViewById(R.id.textData))
                    .setText("Lat - " + location.getLatitude() + " Lng - " + location.getLongitude());
    }

    private void myLocV2() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1 , 0.1f, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                ((TextView) findViewById(R.id.textData))
                        .setText("Lat - " + location.getLatitude() + " Lng - " + location.getLongitude());
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

    public void onMyLoc(View view) {
        myLocV2();
    }
}
