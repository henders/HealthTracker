package com.fortytwodragons.healthtracker.db;

/**
 * Created by shanehender on 7/5/13.
 */
public class HealthMetricType {
    private long id = 0;
    private String name;
    private FieldType type;

    public enum FieldType {
        RANGE,
        BOOL,
        VALUE
    }

    public HealthMetricType(long id, String name, FieldType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }
}
