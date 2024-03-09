package com.imesh.lab.controllers;

import com.google.gson.Gson;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.services.AppointmentService;
import com.imesh.lab.services.CustomerHomeService;
import com.imesh.lab.services.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class CustomerHomeController extends HttpServlet {

    private static CustomerHomeService getCustomerHomeService() {
        return CustomerHomeService.getService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("action-type")) {
            case "Logout":
                logOutCustomer(req, res);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("action-type")) {
            case "GetCustomerTableData":
                getCustomerTableData(req, res);
                break;
            case "CancelAppointment":
                cancelAppointment(req, res);
                break;
        }
    }

    private void cancelAppointment(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            int user_id = (int) req.getSession().getAttribute("id");
            int appointmentId = Integer.parseInt((req.getParameter("appointmentId")));
            response = getCustomerHomeService().cancelAppointment(user_id, appointmentId);
        } catch (ClassNotFoundException | SQLException e) {
            response = new CommonMessageModel("Something went wrong.", false, null);
            e.printStackTrace();
        } finally {
            res.setContentType("text/plain");
            res.getWriter().print(new Gson().toJson(response));
        }
    }

    private void getCustomerTableData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            int userId = (int) req.getSession().getAttribute("id");
            String filter = (String) req.getParameter("filter");
            response = getCustomerHomeService().getCustomerAppointments(userId, filter);
        } catch (ClassNotFoundException | SQLException e) {
            response = new CommonMessageModel("Something went wrong.", false, null);
            e.printStackTrace();
        } finally {
            res.setContentType("text/plain");
            res.getWriter().print(new Gson().toJson(response));
        }
    }

    private void logOutCustomer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        res.sendRedirect(req.getContextPath()+"/login.jsp");
    }

}
