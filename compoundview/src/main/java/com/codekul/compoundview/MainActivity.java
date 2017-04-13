package com.codekul.compoundview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE); // .1
//        inflater = getLayoutInflater(); // .2
//        inflater = LayoutInflater.from(this); // .3
    }

    public void addNew(View view) {

        final LinearLayout layoutInner = (LinearLayout) findViewById(R.id.layoutInner);
        final View viewCompound = inflater.inflate(R.layout.compound_view, layoutInner, false);

        ImageView imageView = (ImageView) viewCompound.findViewById(R.id.imageView);
        TextView textView = (TextView) viewCompound.findViewById(R.id.textView);

        viewCompound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInner.removeView(v);
            }
        });

        layoutInner.addView(viewCompound);
    }
}
