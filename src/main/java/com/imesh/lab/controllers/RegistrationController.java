package com.imesh.lab.controllers;

import com.google.gson.Gson;
import com.imesh.lab.models.UserModel;
import com.imesh.lab.services.AuthenticationService;
import com.imesh.lab.utils.data_mapper.DataMapper;
import com.imesh.lab.utils.mail.EmailSender;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationController extends HttpServlet {
    private static DataMapper getDataMapper() {
        return DataMapper.getDataMapper();
    }

    private static AuthenticationService getAuthenticationService() {
        return AuthenticationService.getService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        switch (req.getParameter("action-type")) {
            case "Register":
                registerUser(req, res);
                break;
        }
    }

    void registerUser(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String message = null;
        boolean hasErrored = false;
        try {
            UserModel userData = new Gson().fromJson(getDataMapper().mapData(req), UserModel.class);

            boolean isCreated = getAuthenticationService().registerUser(userData);
            if (!isCreated) {
                hasErrored = true;
                message = "Something went wrong.";
            } else {
                EmailSender.sendMail("Imesh562@gmail.com", "Test Subject", "This is a test email from Java.");
                message = "We have sent you an E-mail. Please check yor email and login.";
            }
        } catch (ClassNotFoundException | SQLException e) {
            hasErrored = true;
            message = "Something went wrong.";
            e.printStackTrace();
        } finally {
            req.setAttribute("message", message);
            if (hasErrored) {
                req.setAttribute("title", "Operation Failed.");
                req.setAttribute("icon", "error");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/signup.jsp");
                requestDispatcher.forward(req, res);
            } else{
                req.setAttribute("title", "Operation Success.");
                req.setAttribute("icon", "success");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, res);
            }
        }
    }
}
