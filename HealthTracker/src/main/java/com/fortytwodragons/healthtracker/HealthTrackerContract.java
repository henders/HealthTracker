package com.fortytwodragons.healthtracker;

import android.provider.BaseColumns;

/**
 * Created by shanehender on 6/15/13.
 */
public final class HealthTrackerContract {
    public HealthTrackerContract() {}

    public static abstract class HealthEntry implements BaseColumns {
        public static final String TABLE_NAME = "healthentry";

        public static final String COLUMN_HAPPINESS = "happiness";
        public static final String COLUMN_SKIN = "skin";
        public static final String COLUMN_SLEEP = "sleep";
        public static final String COLUMN_DETAILS = "details";
    }
}
