package com.fortytwodragons.healthtracker;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by shanehender on 7/1/13.
 */
public class HealthListViewAdapter extends ArrayAdapter {
    private final Context context;

    public HealthListViewAdapter(Context context, String[] values) {
        super(context, R.layout.activity_entry_list, values);

        this.context = context;
    }
}
