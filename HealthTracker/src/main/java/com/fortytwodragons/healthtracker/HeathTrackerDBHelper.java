package com.fortytwodragons.healthtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shanehender on 6/15/13.
 */
public class HeathTrackerDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "HealthTracker.db";
    public static final int DB_VERSION = 1;

    public HeathTrackerDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.w(this.getClass().getSimpleName(), "Creating the DB");

        String healthTable = "CREATE TABLE healthentry (" +
                HealthTrackerContract.HealthEntry._ID + " integer primary key," +
                HealthTrackerContract.HealthEntry.COLUMN_HAPPINESS + " int," +
                HealthTrackerContract.HealthEntry.COLUMN_SKIN + " int," +
                HealthTrackerContract.HealthEntry.COLUMN_SLEEP + " int," +
                HealthTrackerContract.HealthEntry.COLUMN_DETAILS + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(healthTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        Log.w(this.getClass().getSimpleName(), "Updating the DB?");

    }
}
