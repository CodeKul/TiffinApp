package com.codekul.sensors;

import android.app.ProgressDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    private SensorManager manager;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        handler = new Handler(Looper.getMainLooper());

        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            Log.i("@codekul", sensor.getName());
        }

        accln();
    }

    public void onOkay(View view) {
        startSync();
    }

    private void light() {
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        manager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                ((TextView) findViewById(R.id.txtInfo)).setText("" + event.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void proximity() {
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        manager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                ((TextView) findViewById(R.id.txtInfo)).setText("" + event.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void accln() {
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                String data = "X - " + event.values[0] + " Y - " + event.values[1] + " Z - " + event.values[2];
                ((TextView) findViewById(R.id.txtInfo)).setText(data);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void startSync() {
        final SyncUtils utils = new SyncUtils(this);

        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "Title", "Message");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 1; i <= 10; i++)
                        utils.postMultiPart(i);

                    String res = utils.getBlocking("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();

                } finally {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                        }
                    });
                }
            }
        }).start();
    }
}
