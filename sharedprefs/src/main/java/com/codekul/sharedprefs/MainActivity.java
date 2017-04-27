package com.codekul.sharedprefs;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        //prefs = getPreferences(MODE_PRIVATE);
    }

    public void writePrefs(View view) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("dataBoolean", true);
        editor.putString("dataString", "codekul.com");
        editor.apply();
    }

    public void readPrefs(View view) {
        String builder = "\n Boolean - " + prefs.getBoolean("dataBoolean", false) +
                "\n String - " + prefs.getString("dataString", "none");
        ((TextView)findViewById(R.id.textInfo)).setText(builder);
    }
}
