package com.imesh.lab.controllers;

import com.google.gson.Gson;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.LoginModel;
import com.imesh.lab.models.TestModel;
import com.imesh.lab.services.AppointmentService;
import com.imesh.lab.services.LoginService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewAppointmentController extends HttpServlet {

    private static AppointmentService getAppointmentService() {
        return AppointmentService.getService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("action-type")) {
            case "Labtest":
                getLabTests(req, res);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void getLabTests(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        CommonMessageModel message = new CommonMessageModel("Something went wrong.", false);
        List<TestModel> tests =  new ArrayList<>();
        try {
            tests.addAll(getAppointmentService().getAllLabTests());
        } catch (ClassNotFoundException | SQLException e) {
            message = new CommonMessageModel("Something went wrong.", false);
            e.printStackTrace();
        } finally {
            if (!message.isSuccess()) {
                req.setAttribute("message", message.getMessage());
                req.setAttribute("title", "Operation Failed.");
                req.setAttribute("icon", "error");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("");
                requestDispatcher.forward(req, res);
            } else {
                req.setAttribute("labTests", tests);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("");
                requestDispatcher.forward(req, res);
            }
        }
    }
}
