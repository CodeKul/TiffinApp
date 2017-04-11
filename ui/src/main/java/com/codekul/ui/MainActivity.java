package com.codekul.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
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
    }
}
