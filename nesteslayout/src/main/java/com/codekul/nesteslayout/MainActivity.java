package com.codekul.nesteslayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void plane(View view) {
        changeImage(R.drawable.plane);
    }

    public void bike(View view) {
        changeImage(R.drawable.bike);
    }

    public void train(View view) {
        ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.train);
    }

    private void changeImage(int img) {
        final ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(img);
    }
}
