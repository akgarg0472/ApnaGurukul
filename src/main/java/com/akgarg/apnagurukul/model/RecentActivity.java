package com.akgarg.apnagurukul.model;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("unused")
public class RecentActivity {
    private String activity;
    private String date;
    private String time;

    public RecentActivity() {
    }

    public RecentActivity(String activity,
                          String date,
                          String time) {
        this.activity = activity;
        this.date = date;
        this.time = time;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String activityDate) {
        this.date = activityDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String activityTime) {
        this.time = activityTime;
    }

    @Override
    public String toString() {
        return "RecentActivity{" +
                "activity='" + activity + '\'' +
                ", activityDate='" + date + '\'' +
                ", activityTime='" + time + '\'' +
                '}';
    }
}
