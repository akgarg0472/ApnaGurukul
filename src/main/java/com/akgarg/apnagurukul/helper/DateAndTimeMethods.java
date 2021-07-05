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
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        String time = df.format(date);

        return time + " IST";
    }
}
