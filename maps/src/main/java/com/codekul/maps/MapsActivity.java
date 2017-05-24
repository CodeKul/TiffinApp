package com.codekul.maps;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Add a marker in Sydney and move the camera
        LatLng pune = new LatLng(18.72, 72.36);
        mMap.addMarker(
                new MarkerOptions()
                        .position(pune)
                        .title("Marker in Sydney")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pune));

        LatLng us = new LatLng(37.0902, 95.7129);
        mMap.addMarker(
                new MarkerOptions()
                        .position(us)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
        );

        mMap.addCircle(
                new CircleOptions()
                        .center(us)
                        .strokeColor(Color.BLACK)
                        .fillColor(Color.RED)
                        .strokeWidth(2)
                        .radius(1000)
        );

        mMap.addPolyline(
                new PolylineOptions()
                        .add(pune, us)
                        .color(Color.RED)
                        .width(1.5f)
        );
    }
}
