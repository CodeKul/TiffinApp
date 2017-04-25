package com.codekul.uithread;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(Looper.getMainLooper());

    }

    public void counter(View view) {
//        new MyTask().execute(1,100);

        //handled();


        counterObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnNext( i -> ((TextView)findViewById(R.id.textCounter)).setText(String.valueOf(i)))
                .doOnComplete( () -> Log.i("@codekul","Completed"))
                .subscribe();
    }

    /* TODO: call this method in counter */
    private void threaded() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    ((TextView)findViewById(R.id.textCounter)).setText(String.valueOf(i));
                }
            }
        }).start();
    }

    private class MyTask extends AsyncTask<Integer/*Params*/, Integer/*progress*/, Boolean/*Result*/> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Ui Thread
        }

        @Override
        protected Boolean/*Result*/ doInBackground(Integer... params/*params*/) {
            // Worker Thread

            for (int i = params[0]; i < params[1]; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean/*Result*/) {
            super.onPostExecute(aBoolean);
            // Ui Thread
        }

        @Override
        protected void onProgressUpdate(Integer... values/*progress , int array*/) {
            super.onProgressUpdate(values);

            //Ui thread
            ((TextView) findViewById(R.id.textCounter)).setText(String.valueOf(values[0]));
        }
    }

    int i = 0;

    private void handled() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.textCounter)).setText(String.valueOf(i));
                        }
                    });
                }
            }
        }).start();
    }

    private Observable<Integer> counterObservable() {
        return Observable.create(emitter -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
        });
    }
}
