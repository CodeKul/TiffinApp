package com.codekul.location;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

public class MyService extends Service {

    private LocationManager manager;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        myLocV2();
        return START_STICKY_COMPATIBILITY;
    }

    private void myLocV2() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,1 , 0.1f, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // send via http
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                if(provider.equals(LocationManager.GPS_PROVIDER)) {

                }
            }
        });
    }
}
