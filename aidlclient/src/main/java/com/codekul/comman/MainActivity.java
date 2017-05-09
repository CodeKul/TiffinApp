package com.codekul.comman;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ICar car;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            car = ICar.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            car = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bind(View view) {
        Intent intent = new Intent("com.aidl.comman.car");
        bindService(createExplicitFromImplicitIntent(intent), conn, BIND_AUTO_CREATE);
    }

    public void brand(View view) {

        try {
            Log.i("@codekul", "Brand - " + car.brand("any"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Intent createExplicitFromImplicitIntent(Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

// Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

// Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

// Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

// Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;

    }
}
