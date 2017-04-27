package com.codekul.internalstorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Car car = ((App)getApplication()).car();
    }

    public void read(View view) {

        try {
            FileInputStream fis = openFileInput("my.txt");

            StringBuilder builder = new StringBuilder();
            while (true) {
                int ch = fis.read();
                if(ch == -1) break;
                else builder.append((char)ch);
            }

            ((TextView)findViewById(R.id.textInfo)).setText(builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(View view) {

        try {
            FileOutputStream fos = openFileOutput("my.txt", MODE_PRIVATE);
            fos.write("codekul.com".getBytes());
            fos.close();
        } catch (IOException  e) {
            e.printStackTrace();
        }

        File root = new File(getFilesDir(), "myHello.txt");

        ((TextView)findViewById(R.id.textInfo)).setText("Path - " + root.getAbsolutePath());

        String []files = fileList();
        for (String file : files) {
            ((TextView)findViewById(R.id.textInfo)).append("\n"+file);
        }
    }
}
