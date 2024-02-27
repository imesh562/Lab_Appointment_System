package com.imesh.lab.controllers;

import com.google.gson.Gson;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.TestModel;
import com.imesh.lab.services.AppointmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewAppointmentController extends HttpServlet {

    private static AppointmentService getAppointmentService() {
        return AppointmentService.getService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getLabTests(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}

    private void getLabTests(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<TestModel> tests =  new ArrayList<>();
        try {
            tests.addAll(getAppointmentService().getAllLabTests());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            String jsonData = new Gson().toJson(tests);
            req.setAttribute("labTests", jsonData);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/add_appointment.jsp");
            dispatcher.forward(req, res);
        }
    }
}
