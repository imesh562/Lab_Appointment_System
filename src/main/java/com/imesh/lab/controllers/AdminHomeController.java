package com.imesh.lab.controllers;


import com.google.gson.Gson;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.services.AdminHomeService;
import com.imesh.lab.services.CustomerHomeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AdminHomeController extends HttpServlet {

    private static AdminHomeService getAdminHomeService() {
        return AdminHomeService.getService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("actionType")) {
            case "Logout":
                logOutAdmin(req, res);
                break;
            case "DownloadDocument":
                downloadDocument(req, res);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("action-type")) {
            case "GetAllAppointmentsData":
                getAllAppointmentsData(req, res);
                break;
            case "CancelAppointment":
                cancelAppointment(req, res);
                break;
        }
    }

    private void downloadDocument(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int user_id = Integer.parseInt((req.getParameter("userId")));
        int appointmentId = Integer.parseInt((req.getParameter("appointmentId")));
        getAdminHomeService().downloadDocument(user_id, appointmentId, res, req);
    }

    private void cancelAppointment(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            int appointmentId = Integer.parseInt((req.getParameter("appointmentId")));
            response = getAdminHomeService().cancelAppointment(appointmentId);
        } catch (ClassNotFoundException | SQLException e) {
            response = new CommonMessageModel("Something went wrong.", false, null);
            e.printStackTrace();
        } finally {
            res.setContentType("text/plain");
            res.getWriter().print(new Gson().toJson(response));
        }
    }

    private void getAllAppointmentsData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            String filter = (String) req.getParameter("filter");
            response = getAdminHomeService().getCustomerAppointments(filter);
        } catch (ClassNotFoundException | SQLException e) {
            response = new CommonMessageModel("Something went wrong.", false, null);
            e.printStackTrace();
        } finally {
            res.setContentType("text/plain");
            res.getWriter().print(new Gson().toJson(response));
        }
    }

    private void logOutAdmin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        res.sendRedirect(req.getContextPath()+"/login.jsp");
    }

}
