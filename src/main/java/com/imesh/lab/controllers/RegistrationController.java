package com.imesh.lab.controllers;

import com.google.gson.Gson;
import com.imesh.lab.models.CommonMessageModel;
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

    private static EmailSender getEmailSender() {
        return EmailSender.getEmailSender();
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
        CommonMessageModel message = new CommonMessageModel("Something went wrong.", false);
        try {
            UserModel userData = new Gson().fromJson(getDataMapper().mapData(req), UserModel.class);
            userData.setId(getAuthenticationService().generateUserCode());

            message = getAuthenticationService().registerCustomer(userData);
            if (message.isSuccess()) {
                getEmailSender().sendMail(userData.getEMail(), "Welcome to ABC Laboratories",
                        "Dear " + userData.getFirstName() + ",\n" +
                                "We're providing you with your login ID to access ABC Laboratory:\n" +
                                "\n" +
                                "Login ID: " + userData.getId() + "\n" +
                                "\n" +
                                "If you have any questions or need further assistance, please don't hesitate to reach out to our support team.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            message = new CommonMessageModel("Something went wrong.", false);
            e.printStackTrace();
        } finally {
            req.setAttribute("message", message.getMessage());
            if (!message.isSuccess()) {
                req.setAttribute("title", "Operation Failed.");
                req.setAttribute("icon", "error");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/signup.jsp");
                requestDispatcher.forward(req, res);
            } else {
                req.setAttribute("title", "Operation Success.");
                req.setAttribute("icon", "success");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, res);
            }
        }
    }
}
