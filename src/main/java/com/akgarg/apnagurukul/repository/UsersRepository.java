package com.akgarg.apnagurukul.repository;

import com.akgarg.apnagurukul.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {

    Users getUserByUsername(String username);

    Users getUsersByUsernameEquals(String username);
}
