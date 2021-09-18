package com.akgarg.apnagurukul.service;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.model.CSRFToken;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
public class SecurityService {
    private final UsersRepository usersRepository;

    @Autowired
    public SecurityService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // method to verify user authentication and fetch generated CSRF token
    public CSRFToken validateUserAndRetrieveCsrf(Principal principal, HttpServletRequest request) {
        if (principal == null) {
            return new CSRFToken(null, null, "Please login to obtain your CSRF token");
        }

        Users user = this.usersRepository.getUserByUsername(principal.getName());
        if (user == null) {
            return new CSRFToken(null, null, "User validation failed, please try again");
        }

        CsrfToken csrfToken = new CookieCsrfTokenRepository().loadToken(request);
        return new CSRFToken(user.getUsername(), csrfToken == null ? null : csrfToken.getToken(), "Token generated successfully");
    }
}
