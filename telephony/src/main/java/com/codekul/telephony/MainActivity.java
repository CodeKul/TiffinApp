package com.codekul.telephony;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TelephonyManager manager;

    private BroadcastReceiver receiverSms = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("com.codekul.sms.SENT")) {
                Toast.makeText(context, "Msg Send", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Msg Delievered", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        String imei = manager.getDeviceId();
        Log.i("@codekul", "Imei - "+imei);
        String country = manager.getSimCountryIso();
        Log.i("@codekul", "Country ISO - "+country);
        Log.i("@codekul", "Sim Serial - "+manager.getSimSerialNumber());

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.codekul.sms.SENT");
        filter.addAction("com.codekul.sms.DELIVERED");
        registerReceiver(receiverSms, filter);
    }

    private void sendSms() {

        Intent intentSent = new Intent("com.codekul.sms.SENT");
        PendingIntent sentPendingIntent = PendingIntent
                .getBroadcast(this,1234, intentSent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentDelivered = new Intent("com.codekul.sms.DELIVERED");
        PendingIntent delieveredPendingIntent = PendingIntent
                .getBroadcast(this,1234, intentDelivered, PendingIntent.FLAG_UPDATE_CURRENT);

        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage("9762548833",null, "Hello this is test", sentPendingIntent, delieveredPendingIntent);
    }

    public void sendMsg(View view) {
        sendSms();
    }
}
