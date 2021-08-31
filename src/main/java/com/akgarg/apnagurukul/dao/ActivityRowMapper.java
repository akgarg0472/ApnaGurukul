package com.akgarg.apnagurukul.dao;

import com.akgarg.apnagurukul.model.RecentActivity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityRowMapper implements RowMapper<RecentActivity> {
    @Override
    public RecentActivity mapRow(ResultSet resultSet,
                                 int i) throws SQLException {
        return new RecentActivity(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
    }
}
