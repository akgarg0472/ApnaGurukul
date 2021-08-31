package com.akgarg.apnagurukul.dao;

import com.akgarg.apnagurukul.model.Notification;
import com.akgarg.apnagurukul.model.RecentActivity;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@SuppressWarnings({"SqlDialectInspection", "FieldCanBeLocal", "unused"})
@Repository
public class DatabaseDaoImpl implements DatabaseDao {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final UsersRepository usersRepository;

    @Autowired
    public DatabaseDaoImpl(DataSource dataSource,
                           JdbcTemplate jdbcTemplate,
                           UsersRepository usersRepository) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.usersRepository = usersRepository;
        configure();
    }

    private void configure() {
        if (this.dataSource != null && this.jdbcTemplate != null) {
            this.jdbcTemplate.setDataSource(this.dataSource);
        }
    }

    @Override
    public void deleteActivities(String username) {
        if (this.jdbcTemplate != null) {
            List<RecentActivity> recentActivities = this.jdbcTemplate.query("SELECT * FROM activities WHERE username='" + username + "'", new ActivityRowMapper());
            this.jdbcTemplate.execute("DELETE FROM activities WHERE username='" + username + "'");
        }
    }

    @Override
    public void deleteNotifications(String username) {
        List<Notification> notifications = this.jdbcTemplate.query("SELECT * FROM activities WHERE username='" + username + "'", new NotificationRowMapper());
        this.jdbcTemplate.execute("DELETE FROM notifications WHERE username='" + username + "'");
    }
}
