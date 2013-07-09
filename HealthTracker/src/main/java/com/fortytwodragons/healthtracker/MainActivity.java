package com.fortytwodragons.healthtracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

import com.fortytwodragons.healthtracker.db.HealthDAO;
import com.fortytwodragons.healthtracker.db.HealthEntry;
import com.fortytwodragons.healthtracker.db.HealthEntryMetric;
import com.fortytwodragons.healthtracker.db.HealthTrackerContract;
import com.fortytwodragons.healthtracker.db.HeathTrackerDBHelper;

import java.util.ArrayList;
import java.util.Date;

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

        ArrayList<HealthEntryMetric> metrics = new ArrayList<HealthEntryMetric>();
        HealthEntry newEntry = new HealthEntry(0, happiness.getProgress(), "...details", new Date(), metrics);

        int sleepValue = sleep.getProgress();
        int happinessValue = happiness.getProgress();
        int skinValue = skin.getProgress();

        Log.i("Stuff", "Happiness = " + happinessValue);
        Log.i("Stuff", "Skin = " + skinValue);
        Log.i("Stuff", "Sleep = " + sleepValue);

        HealthDAO dao = new HealthDAO(this.getApplicationContext());
        dao.saveEntry(newEntry);

        Log.i("Stuff", "New id=" + newEntry.getId());

        Toast.makeText(this.getApplicationContext(), "Thanks for sharing!", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, EntryListActivity.class);
//        startActivity(intent);
    }

}
