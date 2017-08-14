package com.wildbeeslabs.rest.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedServiceHandler implements AccessDeniedHandler {

    private String errorPage;

    public AccessDeniedServiceHandler() {
    }

    public AccessDeniedServiceHandler(final String errorPage) {
        this.errorPage = errorPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException ex) throws IOException, ServletException {
        response.getOutputStream().print("Permissions are not provided");
        response.setStatus(403);
        //response.sendRedirect(errorPage);
    }
}
