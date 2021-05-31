package com.akgarg.apnagurukul.security;

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


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        boolean hasAdminRole = false;
        boolean hasTeacherRole = false;
        boolean hasStudentRole = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                hasAdminRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_FACULTY")) {
                hasTeacherRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_STUDENT")) {
                hasStudentRole = true;
                break;
            }
        }

        if (hasAdminRole) {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/admin/dashboard");
        }
        if (hasTeacherRole) {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/faculty/dashboard");
        }
        if (hasStudentRole) {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/student/dashboard");
        } else {
            throw new IllegalStateException("User is not a valid Apna Gurukul user");
        }
    }
}
