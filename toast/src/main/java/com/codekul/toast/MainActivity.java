package com.codekul.toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, "Android", Toast.LENGTH_SHORT);
        Button btn = new Button(this);
        btn.setText(getResources().getString(R.string.hello));
        toast.setView(btn);
        toast.setGravity(Gravity.TOP, 0 , 0);
        toast.show();
    }
}
