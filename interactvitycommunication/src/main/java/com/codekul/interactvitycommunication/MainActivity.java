package com.codekul.interactvitycommunication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    public static final int REQ_NXT = 7562;
    public static final String KEY_MY_NM = "myName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void next(View view) {

        Context context = this;
        Class cls = NextActivity.class;

        EditText edtName = (EditText) findViewById(R.id.edtName);
        String name = edtName.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_MY_NM, name);

        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);

        //startActivity(intent);

        startActivityForResult(intent, REQ_NXT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_NXT) {
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras();
                String dataRes = bundle.getString(NextActivity.KEY_DATA_RES);

                EditText edtName = (EditText) findViewById(R.id.edtName);
                edtName.setText(dataRes);
            }
        }
    }
}
