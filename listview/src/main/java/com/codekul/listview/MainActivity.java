package com.codekul.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageAdapter();
    }

    private void imageAdapter() {

        final List<ImageItem> dataSet = new ArrayList<>();
        dataSet.add(new ImageItem(System.currentTimeMillis(), R.mipmap.ic_launcher, "Normal"));
        dataSet.add(new ImageItem(System.currentTimeMillis(), R.mipmap.ic_launcher_round, "Round"));
        dataSet.add(new ImageItem(System.currentTimeMillis(), R.drawable.plane, "Plane"));

        final ImageAdapter adapter = new ImageAdapter(this, dataSet);

        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
        ((ListView) findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageItem item = (ImageItem) adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                intent.putExtra(ImageActivity.KEY_IMG, item.img);

                startActivity(intent);
            }
        });
    }

    private void arrayAdapter() {
        final List<String> dataSet = new ArrayList<>();
        dataSet.add("Android");
        dataSet.add("Apple");
        dataSet.add("Windows");
        dataSet.add("Samsung");
        dataSet.add("BB");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataSet);

        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
    }

    public void addItem(View view) {
        ImageAdapter adapter = (ImageAdapter) ((ListView)findViewById(R.id.listView)).getAdapter();
        ImageItem item = new ImageItem(System.currentTimeMillis(), R.mipmap.ic_launcher_round, ((EditText) findViewById(R.id.edtName)).getText().toString());
        adapter.addNewItem(item);
    }
}
