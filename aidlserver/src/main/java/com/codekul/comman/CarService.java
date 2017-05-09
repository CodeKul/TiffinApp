package com.codekul.comman;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by aniruddha on 9/5/17.
 */

public class CarService extends Service {

    private IBinder binder = new CarImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class CarImpl extends ICar.Stub {
        @Override
        public String brand(String name) throws RemoteException {
            return "Mercedes";
        }
    }
}
