package com.codekul.dialogs;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void alert(View view) {
        FragmentManager manager = getSupportFragmentManager();
        MyDialog dialog = new MyDialog();
        dialog.setAlertClick((di, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                mt("positive");
            } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                mt("negative");
            } else {
                mt("neutral");
            }
        });
        dialog.show(manager, MyDialog.TAG_ALERT);
    }

    private void mt(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void datePicker(View view) {
        MyDialog dialog = new MyDialog();
        dialog.setDateSetListener((di, year, month, dayOfMonth) -> {
            mt("" + dayOfMonth + " - " + month + " - " + year);
        });
        dialog.show(getSupportFragmentManager(), MyDialog.TAG_DATE);
    }

    public void timePicker(View v) {
        showDialog(MyDialog.TAG_TIME);
    }

    private void showDialog(String tag) {

        MyDialog dialog = new MyDialog();
        if (tag.equals(MyDialog.TAG_TIME)) {
            dialog.setTimeSetListener((view, hourOfDay, minute) -> {
                mt("" + hourOfDay + " : " + minute );
            });
        }
        dialog.show(getSupportFragmentManager(), tag);
    }

    public void progress(View view) {
        showDialog(MyDialog.TAG_PROGRESS);
    }

    public void custom(View view) {
        showDialog(MyDialog.TAG_CUSTOM);
    }
}
