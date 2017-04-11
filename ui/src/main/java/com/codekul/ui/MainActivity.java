package com.codekul.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viaXml();
    }
    private void viaCode() {
        final LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setLayoutParams(new ViewGroup.LayoutParams(300,300));

        final EditText edtName = new EditText(this);
        edtName.setHint("User Name");
        edtName.setLayoutParams(new LinearLayout.LayoutParams(300,80));
        rootLayout.addView(edtName);

        setContentView(rootLayout);
    }

    private void viaXml() {
        setContentView(R.layout.activity_main);

        final EditText edtUserName = (EditText) findViewById(R.id.edtUserName);

        //final View.OnClickListener click = new Click();
        final View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndUpper(edtUserName);
            }
        };

        final Button btnOkay = (Button) findViewById(R.id.btnOkay);
        //btnOkay.setOnClickListener(click);
        btnOkay.setOnClickListener(v -> getAndUpper(edtUserName) );
        btnOkay.setOnClickListener(this::clicked);

        //btnOkay.setOnClickListener(new Click());
    }

    private void clicked(View view) {

    }

    private class Click implements  View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }

    private void getAndUpper(EditText edtUserName) {
        String userName = edtUserName.getText().toString();
        edtUserName.setText(userName.toUpperCase());
    }
}
