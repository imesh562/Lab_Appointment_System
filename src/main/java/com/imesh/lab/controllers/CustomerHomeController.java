package com.imesh.lab.controllers;

import com.google.gson.Gson;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.services.CustomerHomeService;

import javax.mail.Quota;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;

public class CustomerHomeController extends HttpServlet {

    private static CustomerHomeService getCustomerHomeService() {
        return CustomerHomeService.getService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("actionType")) {
            case "Logout":
                logOutCustomer(req, res);
                break;
            case "DownloadDocument":
                downloadDocument(req, res);
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

    private void downloadDocument(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int user_id = (int) req.getSession().getAttribute("id");
        int appointmentId = Integer.parseInt((req.getParameter("appointmentId")));
        getCustomerHomeService().downloadDocument(user_id, appointmentId, res, req);
    }

    private void cancelAppointment(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            if (req.getSession(false).getAttribute("id") != null) {
                int user_id = (int) req.getSession().getAttribute("id");
                int appointmentId = Integer.parseInt((req.getParameter("appointmentId")));
                response = getCustomerHomeService().cancelAppointment(user_id, appointmentId, req);
            } else {
                response = new CommonMessageModel("Session Expired.", false, null);
            }
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
            if (req.getSession(false).getAttribute("id") != null) {
                int userId = (int) req.getSession().getAttribute("id");
                String filter = (String) req.getParameter("filter");
                response = getCustomerHomeService().getCustomerAppointments(userId, filter);
            } else {
                response = new CommonMessageModel("Session Expired.", false, null);
            }
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
        if (session.getAttribute("id") != null) {
            session.invalidate();
            res.sendRedirect(req.getContextPath()+"/login.jsp");
        }
    }
}
