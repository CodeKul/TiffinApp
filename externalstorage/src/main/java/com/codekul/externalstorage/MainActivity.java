package com.codekul.externalstorage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void write(View view) {
        writePublic();
    }

    //https://github.com/spring-guides/gs-uploading-files.git
    public void read(View view) {
        readPublic();
    }

    private void writePublic() {
        if(isValid()) {
//            Environment.DIRECTORY_DOWNLOADS
            File fileRoot = new File(Environment.getExternalStoragePublicDirectory(""),"codekul");
            fileRoot.mkdir();

            File file = new File(Environment.getExternalStoragePublicDirectory("codekul"), "my.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("welcome codekul.com".getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readPublic() {
        if(isValid()) {
            File file = new File(Environment.getExternalStoragePublicDirectory("codekul"), "my.txt");

            try {
                FileInputStream fis = new FileInputStream(file);
                StringBuilder builder = new StringBuilder();
                while(true) {
                    int ch = fis.read();
                    if(ch == -1) break;
                    else builder.append((char)ch);
                }
                ((TextView)findViewById(R.id.textInfo)).setText(builder.toString());
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // /storage/emulated/0/Android/data/com.codekul.externalstorage/files/my.txt
            Log.i("@codekul", "Path - "+file.getAbsolutePath() );
        }
    }

    private void readPrivate() {
        if(isValid()) {
            File file = new File(getExternalFilesDir(""), "my.txt");
            File fileDir = new File(getExternalFilesDir(""), "codekul");
            Log.i("@codekul", "Folder is created - "+fileDir.mkdir());

            try {
                FileInputStream fis = new FileInputStream(file);
                StringBuilder builder = new StringBuilder();
                while(true) {
                    int ch = fis.read();
                    if(ch == -1) break;
                    else builder.append((char)ch);
                }
                ((TextView)findViewById(R.id.textInfo)).setText(builder.toString());
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // /storage/emulated/0/Android/data/com.codekul.externalstorage/files/my.txt
            Log.i("@codekul", "Path - "+file.getAbsolutePath() );
        }
    }

    private void writePrivate() {
        if(isValid()) {
            File file = new File(getExternalFilesDir(""), "my.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("welcome codekul.com".getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Boolean isValid() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
