package br.ufg.nocurriculum.services;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    private static final String LOGOUT_SUCCESS_URL = "/";

    public UserDetails getAuthenticatedUser() {
        var context = SecurityContextHolder.getContext();
        var principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails;
        }
        // Anonymous or no authentication.
        return null;
    }

    public boolean isAuthenticated() {
        return getAuthenticatedUser() != null;
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        var logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
            VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
            null
        );
    }
}