package com.imesh.lab.controllers;

import com.imesh.lab.services.AuthenticationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController extends HttpServlet {

    private static AuthenticationService getAuthService() {
        return AuthenticationService.getService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

    }

}
