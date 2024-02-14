package com.imesh.lab.controllers;

import com.imesh.lab.services.AuthService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeController extends HttpServlet {

    private static AuthService getAuthService() {
        return AuthService.getService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

    }

}
