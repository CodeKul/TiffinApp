package com.codekul.assests;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager manager = getAssets();
        try {
            InputStream is = manager.open("my.txt");
            StringBuilder builder = new StringBuilder();
            while(true) {
                int ch = is.read();
                if(ch == -1) break;
                else builder.append((char)ch);
            }

            Log.i("@codekul", builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
