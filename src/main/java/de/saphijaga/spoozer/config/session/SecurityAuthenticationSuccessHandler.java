package de.saphijaga.spoozer.config.session;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.details.SecurityUserDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by samuel on 17.10.15.
 */
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private UserService userService;

    public SecurityAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUserDetails) {
            String id = ((SecurityUserDetails) principal).getId();
            Optional<UserDetails> user = userService.getUserDetails(id);
            request.getSession().setAttribute("user", user.orElse(null));
        }
        response.sendRedirect("/");
    }
}