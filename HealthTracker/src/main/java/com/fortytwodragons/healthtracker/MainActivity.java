package com.fortytwodragons.healthtracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w(this.getClass().getSimpleName(), "OnCreate called!");

        class DBOpener extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                // do database creation

                return null;
            }
        }

        TelephonyManager tMgr =(TelephonyManager)this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String phNumber = tMgr.getLine1Number();
        Log.i("Stuff", "Phone #" + phNumber);

        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void submitFeeling(View view)
    {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
        SeekBar happiness = (SeekBar) findViewById(R.id.happiness);
        SeekBar skin = (SeekBar) findViewById(R.id.skin);
        SeekBar sleep = (SeekBar) findViewById(R.id.sleep);
        TextView details = (TextView) findViewById(R.id.details);

        int sleepValue = sleep.getProgress();
        int happinessValue = happiness.getProgress();
        int skinValue = skin.getProgress();

        Log.i("Stuff", "Happiness = " + happinessValue);
        Log.i("Stuff", "Skin = " + skinValue);
        Log.i("Stuff", "Sleep = " + sleepValue);

        HeathTrackerDBHelper mHeathTrackerDBHelper = new HeathTrackerDBHelper(this.getApplicationContext());
        SQLiteDatabase db = mHeathTrackerDBHelper.getWritableDatabase();
        ContentValues newRow = new ContentValues();
        newRow.put(HealthTrackerContract.HealthEntry.COLUMN_HAPPINESS, happinessValue);
        newRow.put(HealthTrackerContract.HealthEntry.COLUMN_SKIN, skinValue);
        newRow.put(HealthTrackerContract.HealthEntry.COLUMN_SLEEP, sleepValue);
        newRow.put(HealthTrackerContract.HealthEntry.COLUMN_DETAILS, details.getText().toString());
        long id = db.insert(HealthTrackerContract.HealthEntry.TABLE_NAME, null, newRow);
        Log.i("Stuff", "New id=" + id);

        Toast.makeText(this.getApplicationContext(), "Thanks for sharing!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, EntryListActivity.class);
        startActivity(intent);
    }

}
