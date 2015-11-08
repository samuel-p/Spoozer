package de.saphijaga.spoozer.config.session;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by samuel on 08.11.15.
 */
public class SecurityAccessDeniedHandler extends AccessDeniedHandlerImpl {
    private String errorPage;

    public SecurityAccessDeniedHandler(String errorPage) {
        setErrorPage(errorPage);
    }

    @Override
    public void setErrorPage(String errorPage) {
        super.setErrorPage(errorPage);
        this.errorPage = errorPage;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendRedirect(errorPage);
    }
}