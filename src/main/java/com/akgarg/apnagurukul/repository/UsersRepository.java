package com.akgarg.apnagurukul.repository;

import com.akgarg.apnagurukul.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SqlDialectInspection")
public interface UsersRepository extends JpaRepository<Users, String> {

    Users getUserByUsername(String username);


    Users getUsersByUsernameEquals(String username);


    @Modifying
    @Transactional
    @Query(value = "DELETE from activities WHERE username= :email", nativeQuery = true)
    void deleteUserActivities(String email);


    @Modifying
    @Transactional
    @Query(value = "DELETE from notifications WHERE username= :email", nativeQuery = true)
    void deleteUserNotifications(String email);
}