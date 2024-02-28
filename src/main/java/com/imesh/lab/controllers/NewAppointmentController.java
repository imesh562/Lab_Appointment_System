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
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("action-type")) {
            case "GetDisabledDates":
                int test_id = Integer.parseInt(req.getParameter("testId"));
                getDisabledDates(req, res, test_id);
                break;
        }
    }

    private void getDisabledDates(HttpServletRequest req, HttpServletResponse res, int test_id) throws ServletException, IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            response = getAppointmentService().getDisabledDates(test_id);
        } catch (ClassNotFoundException | SQLException e) {
            response = new CommonMessageModel("Something went wrong.", false, null);
            e.printStackTrace();
        } finally {
            res.setContentType("text/plain");
            res.getWriter().print(new Gson().toJson(response));
        }
    }

    private void getLabTests(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        List<TestModel> tests =  new ArrayList<>();
        try {
            response = getAppointmentService().getAllLabTests();
            tests.addAll(response.getData());
        } catch (ClassNotFoundException | SQLException e) {
            response = new CommonMessageModel("Something went wrong.", false, null);
            e.printStackTrace();
        } finally {
            if(response.isSuccess()){
                String jsonData = new Gson().toJson(tests);
                req.setAttribute("labTests", jsonData);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/add_appointment.jsp");
                dispatcher.forward(req, res);
            }else {
                req.setAttribute("message", response.getMessage());
                req.setAttribute("title", "Operation Failed.");
                req.setAttribute("icon", "error");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/add_appointment.jsp");
                requestDispatcher.forward(req, res);
            }
        }
    }
}
