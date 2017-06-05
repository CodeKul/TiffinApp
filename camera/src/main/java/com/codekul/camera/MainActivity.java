package com.codekul.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import io.reactivex.functions.Consumer;
import me.shaohui.advancedluban.Luban;

public class MainActivity extends AppCompatActivity {

    public static int REQ_CAPTURE = 1234;
    public static int REQ_VIDEO = 4567;
    private Uri imageUri;
    private File captured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void capture(View view) {

        captured = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), System.currentTimeMillis() + ".jpg");
        imageUri = Uri.fromFile(captured);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captured));

        startActivityForResult(intent, REQ_CAPTURE);
    }

    public void record(View view) {

        captured = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), System.currentTimeMillis() + ".mp4");
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, captured.getPath());
        startActivityForResult(takeVideoIntent, REQ_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CAPTURE) {
            if (resultCode == RESULT_OK) {

              /*
               // without compression
               try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                compress(captured);
            }
        }
        else if(requestCode == REQ_VIDEO) {
            if(resultCode == RESULT_OK) {
                VideoView view = ((VideoView)findViewById(R.id.video));
                view.setMediaController(new MediaController(this));
                view.setVideoURI(data.getData());
            }
        }
    }

    private void compress(File file) {
        Luban.compress(this, file)
                .putGear(Luban.FIRST_GEAR)
                .asObservable()
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {

                        byte[] imgByts = new byte[(int) file.length()];
                        FileInputStream fis = new FileInputStream(file);
                        fis.read(imgByts);
                        fis.close();

                        FileOutputStream fos = new FileOutputStream(captured);
                        fos.write(imgByts);
                        fos.close();

                        ((ImageView) findViewById(R.id.imageView)).setImageURI(Uri.fromFile(file));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
