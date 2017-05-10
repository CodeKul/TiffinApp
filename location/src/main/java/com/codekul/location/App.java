package com.codekul.location;

import android.app.Application;
import android.location.Geocoder;
/**
 * Created by aniruddha on 10/5/17.
 */

public class App extends Application {

    private Geocoder geoCoder;

    @Override
    public void onCreate() {
        super.onCreate();

        geoCoder = new Geocoder(this);

    }

    public Geocoder geocoder() {
        return geoCoder;
    }
}
