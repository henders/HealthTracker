package com.fortytwodragons.healthtracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shanehender on 7/5/13.
 */
public class HealthDAO {
    private SQLiteDatabase database;
    private HeathTrackerDBHelper heathTrackerDBHelper;

    public HealthDAO(Context context) {
        heathTrackerDBHelper = new HeathTrackerDBHelper(context);
        database = heathTrackerDBHelper.getWritableDatabase();
    }

    public HealthMetricType createType(String name, HealthMetricType.FieldType type) {
        ContentValues values = new ContentValues();
        values.put(HealthTrackerContract.HealthMetricType._NAME, name);
        values.put(HealthTrackerContract.HealthMetricType._TYPE, type.name());

        long newRowId = database.insert(HealthTrackerContract.HealthMetricType.TABLE_NAME, null, values);

        return new HealthMetricType(newRowId, name, type);
    }

    public ArrayList<HealthMetricType> getAllTypes() {
        ArrayList<HealthMetricType> list = new ArrayList<HealthMetricType>();
        String[] allCols = {
                HealthTrackerContract.HealthMetricType._ID,
                HealthTrackerContract.HealthMetricType._NAME,
                HealthTrackerContract.HealthMetricType._TYPE
        };

        Cursor cursor = database.query(HealthTrackerContract.HealthMetricType.TABLE_NAME, allCols, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HealthMetricType newType = new HealthMetricType(
                    cursor.getLong(cursor.getColumnIndex(HealthTrackerContract.HealthMetricType._ID)),
                    cursor.getString(cursor.getColumnIndex(HealthTrackerContract.HealthMetricType._NAME)),
                    HealthMetricType.FieldType.valueOf(cursor.getString(cursor.getColumnIndex(HealthTrackerContract.HealthMetricType._TYPE)))
            );
            list.add(newType);
        }

        return list;
    }


    public HealthMetricType getType(long id) {
        String[] allCols = {
                HealthTrackerContract.HealthMetricType._ID,
                HealthTrackerContract.HealthMetricType._NAME,
                HealthTrackerContract.HealthMetricType._TYPE
        };
        String selection = HealthTrackerContract.HealthMetricType._ID + "=" + id;
        Cursor cursor = database.query(HealthTrackerContract.HealthMetricType.TABLE_NAME, allCols, selection, null, null, null, null);
        cursor.moveToNext();
        HealthMetricType type = new HealthMetricType(
                cursor.getLong(cursor.getColumnIndex(HealthTrackerContract.HealthMetricType._ID)),
                cursor.getString(cursor.getColumnIndex(HealthTrackerContract.HealthMetricType._NAME)),
                HealthMetricType.FieldType.valueOf(cursor.getString(cursor.getColumnIndex(HealthTrackerContract.HealthMetricType._TYPE)))
        );

        return type;
    }

    public boolean deleteType(HealthMetricType type) {
        int numDeletes = database.delete(HealthTrackerContract.HealthMetricType.TABLE_NAME,
                HealthTrackerContract.HealthMetricType._ID + "=" + type.getId(), null);
        return true;
    }

    /**
     * Saves a health entry to the DB, if it already exists, it updates it.
     *
     * @param entry
     * @return
     */
    public boolean saveEntry(HealthEntry entry) {
        Log.i("Stuff", "SaveEntry() enter");

        ContentValues values = new ContentValues();
        if (entry.getId() > 0) {
            values.put(HealthTrackerContract.HealthEntry._ID, entry.getId());
        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        values.put(HealthTrackerContract.HealthEntry._HAPPINESS, entry.getHappiness());
        values.put(HealthTrackerContract.HealthEntry._DETAILS, entry.getDetails());
        values.put(HealthTrackerContract.HealthEntry._DATE, entry.getDate().toString());

        entry.setId(database.insert(HealthTrackerContract.HealthEntry.TABLE_NAME, null, values));
        Log.i("Stuff", "SaveEntry() new rowid=" + entry.getId());

        // now insert the individual metrics
        for (HealthEntryMetric metric : entry.getMetrics()) {
            Log.i("Stuff", "SaveEntry() saving metric: " + metric.getType().getName());
            ContentValues metricValues = new ContentValues();

            if (metric.id > 0) {
                metricValues.put(HealthTrackerContract.HealthEntryMetric._ID, metric.id);
            }
            metricValues.put(HealthTrackerContract.HealthEntryMetric._VALUE, metric.value);
            metricValues.put(HealthTrackerContract.HealthEntryMetric._TYPE, metric.type.getId());
            metricValues.put(HealthTrackerContract.HealthEntryMetric._ENTRY, entry.getId());

            metric.id = database.insert(HealthTrackerContract.HealthEntryMetric.TABLE_NAME, null, metricValues);
        }

        return true;
    }

    public ArrayList<HealthEntry> getAllEntries() {
        ArrayList<HealthEntry> list = new ArrayList<HealthEntry>();
        String[] allEntryCols = {
                HealthTrackerContract.HealthEntry._ID,
                HealthTrackerContract.HealthEntry._HAPPINESS,
                HealthTrackerContract.HealthEntry._DATE,
                HealthTrackerContract.HealthEntry._DETAILS
        };

        Cursor entryCursor = database.query(HealthTrackerContract.HealthEntry.TABLE_NAME, allEntryCols, null, null, null, null, null);
        while (entryCursor.moveToNext()) {
            Date date = new Date(entryCursor.getString(entryCursor.getColumnIndex(HealthTrackerContract.HealthEntry._DATE)));
            HealthEntry entry = new HealthEntry(
                    entryCursor.getLong(entryCursor.getColumnIndex(HealthTrackerContract.HealthEntry._ID)),
                    entryCursor.getLong(entryCursor.getColumnIndex(HealthTrackerContract.HealthEntry._HAPPINESS)),
                    entryCursor.getString(entryCursor.getColumnIndex(HealthTrackerContract.HealthEntry._DETAILS)),
                    date,
                    null
            );

            // Now want to read all the metrics for this entry
            String[] allMetricCols = {
                    HealthTrackerContract.HealthEntryMetric._ID,
                    HealthTrackerContract.HealthEntryMetric._TYPE,
                    HealthTrackerContract.HealthEntryMetric._VALUE,
                    HealthTrackerContract.HealthEntryMetric._ENTRY
            };
            Cursor metricCursor = database.query(HealthTrackerContract.HealthEntry.TABLE_NAME, allEntryCols, null, null, null, null, null);
            while (metricCursor.moveToNext()) {
                // Get the associated type
                HealthMetricType metricType = getType(metricCursor.getLong(metricCursor.getColumnIndex(HealthTrackerContract.HealthEntryMetric._TYPE)));

                HealthEntryMetric metric = new HealthEntryMetric(
                        metricCursor.getLong(metricCursor.getColumnIndex(HealthTrackerContract.HealthEntryMetric._ID)),
                        metricType,
                        metricCursor.getLong(metricCursor.getColumnIndex(HealthTrackerContract.HealthEntryMetric._VALUE))
                );
                entry.getMetrics().add(metric);
            }

            list.add(entry);
        }

        return list;
    }

    public boolean deleteEntry(HealthEntry entry) {
        int numDeletes = database.delete(HealthTrackerContract.HealthEntry.TABLE_NAME,
                HealthTrackerContract.HealthEntry._ID + "=" + entry.getId(), null);
        return true;
    }

}
