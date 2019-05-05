package com.hch.hooney.filecontrolproject.Databases;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private final String TAG = DatabaseOpenHelper.class.getSimpleName();

    public DatabaseOpenHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "DB onCreate...");
        String create_table =
                        "CREATE TABLE IF NOT EXISTS main_data("+
                        "i_id       INTEGER     PRIMARY KEY AUTOINCREMENT, "+
                        "i_name     text    NOT NULL, "+
                        "i_post     int     NOT NULL, "+
                        "i_adr1     text    NOT NULL, "+
                        "i_adr2     text    NOT NULL "+
                        ")";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String remove_table =
                "DROP TABLE IF EXISTS main_data";
        db.execSQL(remove_table);
        onCreate(db);
    }
}
