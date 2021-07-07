package com.akgarg.apnagurukul.security;

import com.akgarg.apnagurukul.entity.Users;
import com.akgarg.apnagurukul.helper.DateAndTimeMethods;
import com.akgarg.apnagurukul.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        boolean hasAdminRole = false;
        boolean hasTeacherRole = false;
        boolean hasStudentRole = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            //noinspection IfCanBeSwitch
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                hasAdminRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_FACULTY")) {
                hasTeacherRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_STUDENT")) {
                hasStudentRole = true;
            }
        }

        Users user = this.usersRepository.getUserByUsername(authentication.getName());
        if (user != null) {
            user.setLastLoginDate(DateAndTimeMethods.getCurrentTime());
            this.usersRepository.save(user);
        }

        if (hasAdminRole) {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/admin/dashboard");
        } else if (hasTeacherRole) {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/faculty/dashboard");
        } else if (hasStudentRole) {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/student/dashboard");
        } else {
            throw new IllegalStateException("User is not a valid ApnaGurukul user");
        }
    }
}