package com.codekul.savingstate;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if(savedInstanceState != null) {
            ((TextView)findViewById(R.id.txtInfo)).setText(savedInstanceState.getString("key_my_data"));
        }*/
        mt("onCreate");
    }

    public void changeData(View view) {
        ((TextView)findViewById(R.id.txtInfo)).setText(new Date().toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mt("onSavedInstanceState");
        outState.putString("key_my_data", ((TextView)findViewById(R.id.txtInfo)).getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mt("onRestoreInstanceState");
        if(savedInstanceState != null) {
            ((TextView)findViewById(R.id.txtInfo)).setText(savedInstanceState.getString("key_my_data"));
        }
    }

    @Override
    protected void onDestroy() {
        mt("onDestroy");
        super.onDestroy();
    }

    private void mt(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void newAct(View view) {
        startActivity(new Intent(this,NextActivity.class));
    }
}
