package com.codekul.location;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.functions.Consumer;
import me.shaohui.advancedluban.Luban;

public class CameraFinderActivity extends AppCompatActivity implements LocationListener {

    private LocationManager manager;
    public static final int PERMISSION_LOCATION = 1234;

    public static int REQ_CAPTURE = 7896;
    private File captured;
    private Uri imageUri;

    private ProgressDialog dialog;
    private long locationMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_finder);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            dialog = ProgressDialog.show(this, "Title", "Message");
            captured = new File(getFilesDir(), System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(captured);
            listenLocations();
        } else {
            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(viewIntent, REQ_CAPTURE);
        }
    }


    private void listenLocations() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // if permission not granted

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Title")
                        .setMessage("Message")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions(
                                        CameraFinderActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_LOCATION
                                );
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_LOCATION
                );
            }
        } else {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    100,
                    0.1f,
                    this
            );
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        locationMillis = System.currentTimeMillis();
        dialog.dismiss();
        manager.removeUpdates(this);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captured));
        startActivityForResult(intent, REQ_CAPTURE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //listenLocations();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CAPTURE) {
            if (resultCode == RESULT_OK) {

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void compress(File file) {
        Luban.compress(this, file)
                .putGear(Luban.FIRST_GEAR)
                .asObservable()
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {

                        byte[] imgByts = new byte[(int) file.length()];
                        FileInputStream fis = new FileInputStream(file);
                        fis.read(imgByts);
                        fis.close();

                        FileOutputStream fos = new FileOutputStream(captured);
                        fos.write(imgByts);
                        fos.close();

                        ((ImageView) findViewById(R.id.imageView)).setImageURI(Uri.fromFile(file));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    public void onRetake(View view) {

    }

    public void onSave(View view) {

        Log.i("@codekul", "Difference "+(System.currentTimeMillis() - locationMillis));
        if ((System.currentTimeMillis() - locationMillis) <= 90000) {
            compress(captured);
        }
        else {
            Intent intent = new Intent(this, CameraFinderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key1", "id");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
