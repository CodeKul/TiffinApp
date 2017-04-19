package com.codekul.externalizingresources;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String []countries = getResources().getStringArray(R.array.country);

        int color = getResources().getColor(R.color.faintGray);
        color = ContextCompat.getColor(this, R.color.faintGray);

        ImageView img = (ImageView) findViewById(R.id.imgVw);

        File f = new File("/", "hello.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clicked(View view) {
        Log.i(TAG, "Clicked");
    }
}
