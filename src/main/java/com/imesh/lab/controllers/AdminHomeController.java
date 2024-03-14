package com.imesh.lab.controllers;


import com.google.gson.Gson;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.services.AdminHomeService;
import com.imesh.lab.services.CustomerHomeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

@MultipartConfig
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
            case "ConfirmPayment":
                confirmPayment(req, res);
                break;
            case "FileUpload":
                uploadDocument(req, res);
                break;
        }
    }

    private void uploadDocument(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            if (req.getSession(false).getAttribute("id") != null) {
                response = getAdminHomeService().uploadDocument(req);
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

    private void confirmPayment(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            if (req.getSession(false).getAttribute("id") != null) {
                int appointmentId = Integer.parseInt((req.getParameter("appointmentId")));
                String email = req.getParameter("customerEmail");
                response = getAdminHomeService().confirmPayment(appointmentId, req, email);
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

    private void downloadDocument(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int user_id = Integer.parseInt((req.getParameter("userId")));
        int appointmentId = Integer.parseInt((req.getParameter("appointmentId")));
        getAdminHomeService().downloadDocument(user_id, appointmentId, res, req);
    }

    private void cancelAppointment(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            if (req.getSession(false).getAttribute("id") != null) {
                int appointmentId = Integer.parseInt((req.getParameter("appointmentId")));
                String email = req.getParameter("customerEmail");
                response = getAdminHomeService().cancelAppointment(appointmentId, req, email);
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

    private void getAllAppointmentsData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        CommonMessageModel response = new CommonMessageModel("Something went wrong.", false, null);
        try {
            if (req.getSession(false).getAttribute("id") != null) {
                String filter = (String) req.getParameter("filter");
                response = getAdminHomeService().getCustomerAppointments(filter);
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

    private void logOutAdmin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        if (session.getAttribute("id") != null) {
            session.invalidate();
            res.sendRedirect(req.getContextPath()+"/login.jsp");
        }
    }

}
