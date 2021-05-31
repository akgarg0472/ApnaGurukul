package com.akgarg.apnagurukul.helper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateAndTimeMethods {

    public static String getLastLoginTime() {
        StringBuilder lastLogin = new StringBuilder();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        lastLogin.append(timeFormatter.format(LocalTime.now()));

        TimeZone timeZone = TimeZone.getDefault();
        String zoneName = timeZone.getDisplayName();
        String[] array = zoneName.split(" ");

        StringBuilder timeZoneID = new StringBuilder();
        for (String s : array) {
            timeZoneID.append(s.charAt(0));
        }

        lastLogin.append(" ");
        lastLogin.append(timeZoneID);

        return lastLogin.toString();
    }
}
