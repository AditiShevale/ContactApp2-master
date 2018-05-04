package com.example.aditi.contactapp2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fav.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + Contract.Fav.TABLE_NAME + " (" +
                Contract.Fav._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.Fav.COLUMN_IMAGE + " NOT NULL, " +
                Contract.Fav.COLUMN_NAME + " NOT NULL, " +
                Contract.Fav.COLUMN_PHONE + " NOT NULL " +
                ");";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + Contract.Fav.TABLE_NAME);
        onCreate(db);
    }
}
