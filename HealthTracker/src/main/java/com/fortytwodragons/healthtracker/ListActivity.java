package com.fortytwodragons.healthtracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        readHealthEntries();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }


    public void readHealthEntries()
    {
        HeathTrackerDBHelper mHeathTrackerDBHelper = new HeathTrackerDBHelper(this.getApplicationContext());
        SQLiteDatabase db = mHeathTrackerDBHelper.getWritableDatabase();


        String[] projection = {
                HealthTrackerContract.HealthEntry._ID,
                HealthTrackerContract.HealthEntry.COLUMN_SLEEP,
                HealthTrackerContract.HealthEntry.COLUMN_HAPPINESS,
                HealthTrackerContract.HealthEntry.COLUMN_SKIN,
                HealthTrackerContract.HealthEntry.COLUMN_DETAILS
        };

        try {
            Cursor c = db.query(
                    HealthTrackerContract.HealthEntry.TABLE_NAME,  // The table to query
                    projection,                               // The columns to return
                    null,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );
            c.moveToFirst();

            do {
                Long id = c.getLong(c.getColumnIndexOrThrow(HealthTrackerContract.HealthEntry._ID));
                Long happiness = c.getLong(c.getColumnIndexOrThrow(HealthTrackerContract.HealthEntry.COLUMN_HAPPINESS));
                Long skin = c.getLong(c.getColumnIndexOrThrow(HealthTrackerContract.HealthEntry.COLUMN_SKIN));
                Long sleep = c.getLong(c.getColumnIndexOrThrow(HealthTrackerContract.HealthEntry.COLUMN_SLEEP));
                String details = c.getString(c.getColumnIndexOrThrow(HealthTrackerContract.HealthEntry.COLUMN_DETAILS));
                Log.i("Stuff", "Found row: " + id);
                Log.i("Stuff", "    happiness: " + happiness);
                Log.i("Stuff", "    sleep: " + sleep);
                Log.i("Stuff", "    happiness: " + skin);
                Log.i("Stuff", "    happiness: " + details);
            } while (c.moveToNext());
        }
        catch (Exception ex) {

        }
    }

}
