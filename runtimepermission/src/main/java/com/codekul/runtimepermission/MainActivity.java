package com.codekul.runtimepermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static int PERMISSION_WRITE = 4562;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void writePublic() {
        if(isValid()) {
//            Environment.DIRECTORY_DOWNLOADS
            File fileRoot = new File(Environment.getExternalStoragePublicDirectory(""),"codekul");
            fileRoot.mkdir();

            File file = new File(Environment.getExternalStoragePublicDirectory("codekul"), "my.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("welcome codekul.com".getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean isValid() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public void clicked(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage("We need access of your SD card, for writing")
                        .setPositiveButton("Yes", (di, which)->{

                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSION_WRITE);
                        });
                builder.show();

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_WRITE);
            }
        }
        else writePublic();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_WRITE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                writePublic();
            }
        }
    }
}
