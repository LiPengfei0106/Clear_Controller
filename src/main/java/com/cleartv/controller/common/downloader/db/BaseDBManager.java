package com.cleartv.controller.common.downloader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

public abstract class BaseDBManager {
    protected static final String TAG = BaseDBManager.class.getName();
    private int version = 1;
    protected String databaseName;
    protected Context mContext = null;

    public BaseDBManager(Context mContext, String dbName) {
        this.mContext = mContext;
        if (TextUtils.isEmpty(dbName)) {
            this.databaseName = DBOpenHelper.DEFAULT_DB_NAME;
        } else {
            this.databaseName = dbName;
        }
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public int getVersion() {
        return this.version;
    }

    protected abstract SQLiteDatabase openDatabase();

    protected abstract SQLiteOpenHelper getSQLiteOpenHelper();

}