package com.akgarg.apnagurukul.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateAndTimeMethods {

    public static String getCurrentDate() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));

        return df.format(date);
    }


    public static String getCurrentTime() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        String currentTime = df.format(date);
        int hour = Integer.parseInt(currentTime.substring(0, currentTime.indexOf(':')));
        String minutes = currentTime.substring(currentTime.indexOf(':') + 1);

        return (hour > 12 ? (hour - 12) : hour) + ":" + minutes + " " + (hour >= 12 ? "PM IST" : "AM IST");
    }
}
