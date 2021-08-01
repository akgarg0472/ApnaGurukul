package com.akgarg.apnagurukul.service;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserService {

    private final UsersRepository usersRepository;

    @Autowired
    public GetUserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users getUser(String username) {
        return this.usersRepository.findById(username).orElse(null);
    }
}
