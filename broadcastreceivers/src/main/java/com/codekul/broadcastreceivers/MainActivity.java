package com.codekul.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getBooleanExtra("state", false)) {
                ((ImageView)findViewById(R.id.imagePlane)).setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
            }
            else ((ImageView)findViewById(R.id.imagePlane)).setImageResource(R.drawable.ic_airplanemode_inactive_black_24dp);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.imagePlane).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent("com.codekul.action"));
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, filter);
    }
}
