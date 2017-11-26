package com.vagapov.amir.otzovik.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.vagapov.amir.otzovik.database.DBSchema.DBTable.Columns.*;

/**
 * Created by Tom on 24.11.2017.
 */

public class PlaceOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "places.db";

    public PlaceOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DBSchema.DBTable.TABLE_NAME + "(" + " _id integer primary key autoincrement, " +
        UUID + ", "+ PLACE_NAME + ", " + ADRESS + ", " + PHONE_NUMBER + ", " + WORKTIME + ", "
                + RATING + ", " + PLACE_TYPE + ", " + DESCRIPTION + ", " + FAVORITE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
