package com.codekul.intentsandintentfilters;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sport(View view) {
        Intent intent = new Intent();
        intent.setAction("com.codekul.action.COMAN");
//        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setDataAndType(Uri.parse("http://codekul.com"), "text/html");
        startActivity(intent);
    }

    public void news(View view) {
        browser();
    }

    private void dial() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        startActivity(intent);
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://97624"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    private void browser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("http://codekul.com/training.pdf"),"application/pdf");
        startActivity(intent);
    }
}
