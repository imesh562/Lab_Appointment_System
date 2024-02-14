package com.imesh.lab.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthService {
    private static AuthService authenticationService;

    public static synchronized AuthService getService() {
        if (authenticationService == null) {
            authenticationService = new AuthService();
        }
        return authenticationService;
    }

}
