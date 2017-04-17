package com.codekul.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    public static final String KEY_IMG = "image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        int imgId = intent.getIntExtra(KEY_IMG, R.mipmap.ic_launcher_round);
        ((ImageView)findViewById(R.id.imageView)).setImageResource(imgId);
    }
}
