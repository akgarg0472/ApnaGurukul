package com.akgarg.apnagurukul.dao;

public interface DatabaseDao {
    void deleteActivities(String username);

    void deleteNotifications(String username);
}
