package com.imesh.lab.controllers;

import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.TestModel;
import com.imesh.lab.services.AppointmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewAppointmentController extends HttpServlet {

    private static AppointmentService getAppointmentService() {
        return AppointmentService.getService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("action-type")) {
            case "LabTests":
                getLabTests(req, res);
                break;
        }
    }

    private void getLabTests(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        CommonMessageModel message = new CommonMessageModel("Something went wrong.", false);
        List<TestModel> tests =  new ArrayList<>();
        try {
            tests.addAll(getAppointmentService().getAllLabTests());
            message = new CommonMessageModel("Operation Success.", true);
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
