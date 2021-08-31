package com.akgarg.apnagurukul.dao;

import com.akgarg.apnagurukul.model.Notification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Notification(resultSet.getString(3), resultSet.getString(2), resultSet.getString(4));
    }
}
