package com.akgarg.apnagurukul.security;

import com.akgarg.apnagurukul.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final Users users;

    public UserDetailsImpl(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.users.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.users.getPassword();
    }

    @Override
    public String getUsername() {
        return this.users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.users.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.users.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.users.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.users.isEnabled();
    }
}
