package com.codekul.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by aniruddha on 26/4/17.
 */

public class GolobalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("@codekul", "Action Is "+intent.getAction());
    }
}
