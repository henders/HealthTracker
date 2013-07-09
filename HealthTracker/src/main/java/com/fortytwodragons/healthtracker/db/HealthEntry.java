package com.fortytwodragons.healthtracker.db;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents 1 entry submitted by a user.
 *
 * Created by shanehender on 7/5/13.
 */
public class HealthEntry {
    private long id = 0;
    private long happiness = 0;
    private String details;
    private Date date;
    private ArrayList<HealthEntryMetric> metrics;

    public HealthEntry(long id, long happiness, String details, Date date, ArrayList<HealthEntryMetric> metrics) {
        this.id = id;
        this.happiness = happiness;
        this.details = details;
        this.date = date;
        this.metrics = metrics;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<HealthEntryMetric> getMetrics() {
        return metrics;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getHappiness() {
        return happiness;
    }

    public void setHappiness(long happiness) {
        this.happiness = happiness;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
