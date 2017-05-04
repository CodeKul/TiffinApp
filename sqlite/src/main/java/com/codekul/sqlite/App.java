package com.codekul.sqlite;

import android.app.Application;

import com.codekul.sqlite.domain.DaoMaster;
import com.codekul.sqlite.domain.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by aniruddha on 3/5/17.
 */

public class App extends Application {

    public static final boolean ENCRYPTED = true;

    private DbHelper helperOur;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DbHelper helper() {
        return helperOur == null ? helperOur = new DbHelper(this, Db.NAME, null, Db.VERSION) : helperOur;
    }

    public DaoSession session() {
        return daoSession;
    }
}
