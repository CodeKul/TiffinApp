package com.codekul.interactvitycommunication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {

    public static final String KEY_DATA_RES = "dataRes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        Intent resInt = getIntent();

        Bundle bundle = resInt.getExtras();
        String name = bundle.getString(MainActivity.KEY_MY_NM);

        if(name != null && name.length() > 0) {
            /*TextView textView = (TextView) findViewById(R.id.textInfo);
            textView.setText(name);*/
            text(name);
        }
    }

    public void back(View view) {

       /* TextView textView = (TextView) findViewById(R.id.textInfo);
        String nameUpper = textView.getText().toString();*/

        Bundle bundle = new Bundle();
        //bundle.putString(KEY_DATA_RES, ((TextView) findViewById(R.id.textInfo)).getText().toString());
        bundle.putString(KEY_DATA_RES, text());

        Intent intent = new Intent();
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);

        finish();
    }

    public void upper(View view) {
        /*TextView textView = (TextView) findViewById(R.id.textInfo);
        String nameUpper = textView.getText().toString().toUpperCase();
        textView.setText(nameUpper);*/
        text(text());
    }

    private String text() {
        return ((TextView) findViewById(R.id.textInfo)).getText().toString();
    }
    private void text(String nm) {
        TextView textView = (TextView) findViewById(R.id.textInfo);
        textView.setText(nm);
    }
}
