package com.fortytwodragons.healthtracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shanehender on 6/15/13.
 */
public class HeathTrackerDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "HealthTracker.db";
    public static final int DB_VERSION = 2;

    public HeathTrackerDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.w(this.getClass().getSimpleName(), "Creating the DB");

        String healthEntryTable = "CREATE TABLE " + HealthTrackerContract.HealthEntry.TABLE_NAME + " (" +
                HealthTrackerContract.HealthEntry._ID + " integer primary key," +
                HealthTrackerContract.HealthEntry._HAPPINESS + " int," +
                HealthTrackerContract.HealthEntry._DETAILS + " TEXT," +
                HealthTrackerContract.HealthEntry._DATE + " DATE" +
                ");";

        String healthEntryMetricTable = "CREATE TABLE " + HealthTrackerContract.HealthEntryMetric.TABLE_NAME + " (" +
                HealthTrackerContract.HealthEntryMetric._ID + " integer primary key," +
                HealthTrackerContract.HealthEntryMetric._ENTRY + " int," +
                HealthTrackerContract.HealthEntryMetric._TYPE + " int," +
                HealthTrackerContract.HealthEntryMetric._VALUE + " int," +
                "FOREIGN KEY(" + HealthTrackerContract.HealthEntryMetric._ENTRY + ") REFERENCES " +
                    HealthTrackerContract.HealthEntry.TABLE_NAME + "(" + HealthTrackerContract.HealthEntry._ID + ")," +
                "FOREIGN KEY(" + HealthTrackerContract.HealthEntryMetric._TYPE + ") REFERENCES " +
                    HealthTrackerContract.HealthMetricType.TABLE_NAME + "(" + HealthTrackerContract.HealthMetricType._ID + ")" +
                ");";

        String healthMetricTypeTable = "CREATE TABLE " + HealthTrackerContract.HealthMetricType.TABLE_NAME + " (" +
                HealthTrackerContract.HealthMetricType._ID + " integer primary key," +
                HealthTrackerContract.HealthMetricType._NAME + " TEXT," +
                HealthTrackerContract.HealthMetricType._TYPE + " TEXT" +
                ");";

        sqLiteDatabase.execSQL(healthEntryTable);
        sqLiteDatabase.execSQL(healthMetricTypeTable);
        sqLiteDatabase.execSQL(healthEntryMetricTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        Log.w(this.getClass().getSimpleName(), "Updating the DB?");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HealthTrackerContract.HealthEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HealthTrackerContract.HealthEntryMetric.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HealthTrackerContract.HealthMetricType.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
