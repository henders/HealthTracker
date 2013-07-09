package com.fortytwodragons.healthtracker.db;

/**
 * Created by shanehender on 7/7/13.
 */
public class HealthEntryMetric {
    public long id = 0;
    public HealthMetricType type;
    public long value;

    public HealthEntryMetric(long id, HealthMetricType type, long value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public HealthMetricType getType() {
        return type;
    }

    public long getValue() {
        return value;
    }

}
