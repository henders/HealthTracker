package com.fortytwodragons.healthtracker.db;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by shanehender on 6/15/13.
 */
public abstract class HealthTrackerContract {

    public static abstract class HealthEntry implements BaseColumns {
        public static final String TABLE_NAME = "healthentry";

        public static final String _HAPPINESS = "happiness";
        public static final String _DATE = "entrydate";
        public static final String _DETAILS = "details";
    }

    public static abstract class HealthEntryMetric implements BaseColumns {
        public static final String TABLE_NAME = "healthentrymetric";

        public static final String _ENTRY = "healthentry_id";
        public static final String _TYPE = "type_id";
        public static final String _VALUE = "value";
    }

    public static abstract class HealthMetricType implements BaseColumns {
        public static final String TABLE_NAME = "healthmetrictype";

        public static final String _NAME = "name";
        public static final String _TYPE = "value_type";
    }

}
