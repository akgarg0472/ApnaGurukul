package com.akgarg.apnagurukul.model;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("unused")
public class Notification {
    private String notification;
    private String date;
    private String time;

    public Notification() {
    }

    public Notification(String notification, String date, String time) {
        this.notification = notification;
        this.date = date;
        this.time = time;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String message) {
        this.notification = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + notification + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
