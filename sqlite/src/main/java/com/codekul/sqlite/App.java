package com.codekul.sqlite;

import android.app.Application;

/**
 * Created by aniruddha on 3/5/17.
 */

public class App extends Application {

    private DbHelper helper;

    public DbHelper helper() {
        return helper == null ? helper = new DbHelper(this, Db.NAME, null, Db.VERSION) : helper;
    }
}
