package de.saphijaga.spoozer.config.session;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.authentication.Session;
import de.saphijaga.spoozer.web.details.SecurityUserDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
            request.getSession().setAttribute(Session.USER, user.orElse(null));
            request.getSession().setAttribute(Session.SERVER_URL, getServerUrl(request));
        }
        response.sendRedirect("/");
    }

    private String getServerUrl(HttpServletRequest request) {
        String redirectUrl = request.getScheme() + "://" + request.getServerName();
        if ("http".equals(request.getScheme()) && request.getServerPort() != 80) {
            redirectUrl += ":" + request.getServerPort();
        }
        if ("https".equals(request.getScheme()) && request.getServerPort() != 443) {
            redirectUrl += ":" + request.getServerPort();
        }
        return redirectUrl;
    }
}