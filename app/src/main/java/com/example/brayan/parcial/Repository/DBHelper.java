package com.example.brayan.parcial.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by brayantorreson 10/9/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Eventos.db";

    public DBHelper(Context context ) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBContract.UserEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBContract.EventEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE " + DBContract.UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE " + DBContract.EventEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
