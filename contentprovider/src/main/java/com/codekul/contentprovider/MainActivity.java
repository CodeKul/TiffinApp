package com.codekul.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readDb();
    }

    private void readContacts() {

        List<String> dataSet = new ArrayList<>();

        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER},
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                dataSet.add(name + "\n" + num);
                Log.i("@codekul", "Name is " + name);
            }
            cursor.close();
        }

        ((ListView) findViewById(R.id.listContacts)).setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataSet));
    }

    private void readDb() {

        List<String> dataSet = new ArrayList<>();

        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(
                Uri.parse("content://com.codekul.db.provider"),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("imei"));
                Integer ver = cursor.getInt(cursor.getColumnIndex("ver"));
                String os = cursor.getString(cursor.getColumnIndex("os"));
                dataSet.add(name + "\n" + ver +"\n"+os);
                Log.i("@codekul", "Name is " + name);
            }
            cursor.close();
        }
        else Log.i("@codekul", "Null Cursor");

        ((ListView) findViewById(R.id.listContacts)).setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataSet));
    }
}
