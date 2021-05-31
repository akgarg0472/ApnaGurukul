package com.akgarg.apnagurukul.security;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = this.usersRepository.getUserByUsername(username);

        if (users == null) {
            throw new UsernameNotFoundException(username + " is not a registered user");
        }

        return new UserDetailsImpl(users);
    }
}
