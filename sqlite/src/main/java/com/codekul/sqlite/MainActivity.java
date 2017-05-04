package com.codekul.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codekul.sqlite.domain.Car;
import com.codekul.sqlite.domain.DaoSession;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        raw();

        carInsert();
    }

    public void insert(View view) {
        SQLiteDatabase sqDb = ((App) getApplication()).helper().getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Db.Mobile.COL_IMEI, imei());
        values.put(Db.Mobile.COL_VERSION, ver());
        values.put(Db.Mobile.COL_OS, os());
        if (sqDb.insert(Db.Mobile.TAB_NAME, null, values) != -1)
            Log.i(TAG, "Inserted successfully :)");

        sqDb.close();
    }

    public void display(View view) {
        displayOne();
    }

    private void displayAll() {
        SQLiteDatabase sqDb = ((App) getApplication()).helper().getReadableDatabase();

        String table = Db.Mobile.TAB_NAME;
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = sqDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        while (cursor.moveToNext()) {
            Log.i(TAG, "Imei - " + cursor.getString(cursor.getColumnIndex(Db.Mobile.COL_IMEI)));
            Log.i(TAG, "Version - " + cursor.getInt(cursor.getColumnIndex(Db.Mobile.COL_VERSION)));
            Log.i(TAG, "Os - " + cursor.getString(cursor.getColumnIndex(Db.Mobile.COL_OS)));
        }
        sqDb.close();
    }

    private void displayOne() {

        SQLiteDatabase sqDb = ((App) getApplication()).helper().getReadableDatabase();

        String table = Db.Mobile.TAB_NAME;
        String[] columns = {Db.Mobile.COL_VERSION, Db.Mobile.COL_OS};
        String selection = Db.Mobile.COL_IMEI + " = ?";
        String[] selectionArgs = {imei()};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = sqDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        while (cursor.moveToNext()) {
            ver("" + cursor.getInt(cursor.getColumnIndex(Db.Mobile.COL_VERSION)));
            os(cursor.getString(cursor.getColumnIndex(Db.Mobile.COL_OS)));
        }
        sqDb.close();
    }

    private String imei(String... imei) {
        if (imei.length <= 0)
            return ((EditText) findViewById(R.id.edtImei)).getText().toString();
        ((EditText) findViewById(R.id.edtImei)).setText(imei[0]);
        return null;
    }

    private String ver(String... ver) {
        if (ver.length <= 0)
            return ((EditText) findViewById(R.id.edtVer)).getText().toString();
        ((EditText) findViewById(R.id.edtVer)).setText(ver[0]);
        return null;
    }

    private String os(String... os) {
        if (os.length <= 0)
            return ((EditText) findViewById(R.id.edtOs)).getText().toString();
        ((EditText) findViewById(R.id.edtOs)).setText(os[0]);
        return null;
    }

    public void update(View view) {
        SQLiteDatabase sqDb = ((App) getApplication()).helper().getReadableDatabase();

        String table = Db.Mobile.TAB_NAME;

        ContentValues values = new ContentValues();
        values.put(Db.Mobile.COL_OS, os());
        values.put(Db.Mobile.COL_VERSION, ver());

        String whereClause = Db.Mobile.COL_IMEI + " = ?";
        String[] whereArgs = {imei()};

        if(sqDb.update(table, values, whereClause, whereArgs) > 0) {
            Log.i(TAG, "Updated successfully :)");
        }
        sqDb.close();
    }


    public void delete(View view) {
        SQLiteDatabase sqDb = ((App) getApplication()).helper().getWritableDatabase();

        String table = Db.Mobile.TAB_NAME;
        String whereClause = "imei = ?";
        String[] whereArgs = {imei()};

        if(sqDb.delete(table, whereClause, whereArgs) > 0) {
            Log.i(TAG, "Deleted successfully :)");
        }

        sqDb.close();
    }

    public void raw() {
        SQLiteDatabase sqDb = ((App) getApplication()).helper().getWritableDatabase();
        sqDb.execSQL("insert into mobile values('466', 12, 'apple')");
        sqDb.close();
    }

    private void carInsert() {

        Car car = new Car();
        car.setId(System.currentTimeMillis());
        car.setNm("Audi");

        DaoSession session = ((App)getApplication()).session();
        session.getCarDao().insert(car);

        List<Car> cars = session.getCarDao().loadAll();
        for (Car carMy : cars) {
            Log.i(TAG, "Fetched Car - "+carMy.getNm());
        }
    }
}
