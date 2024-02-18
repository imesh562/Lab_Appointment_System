package com.imesh.lab.controllers;

import com.google.gson.Gson;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.LoginModel;
import com.imesh.lab.models.UserModel;
import com.imesh.lab.services.LoginService;
import com.imesh.lab.services.RegistrationService;
import com.imesh.lab.utils.data_mapper.DataMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginController extends HttpServlet {
    private static DataMapper getDataMapper() {
        return DataMapper.getDataMapper();
    }

    private static LoginService getLoginService() {
        return LoginService.getService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        switch (req.getParameter("action-type")) {
            case "Login":
                loginUser(req, res);
                break;
        }
    }

    void loginUser(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        CommonMessageModel message = new CommonMessageModel("Something went wrong.", false);
        try {
            LoginModel loginData = new Gson().fromJson(getDataMapper().mapData(req), LoginModel.class);
            message = getLoginService().loginUser(loginData, req);
        } catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
            message = new CommonMessageModel("Something went wrong.", false);
            e.printStackTrace();
        } finally {
            if (!message.isSuccess()) {
                req.setAttribute("message", message.getMessage());
                req.setAttribute("title", "Operation Failed.");
                req.setAttribute("icon", "error");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(req, res);
            } else {
                int userType = (int) req.getSession().getAttribute("user_type");
                RequestDispatcher requestDispatcher = null;
                if (userType == 1) {
                    requestDispatcher = req.getRequestDispatcher("admin/admin_index.jsp");
                } else {
                    requestDispatcher = req.getRequestDispatcher("customer/customer_index.jsp");
                }
                requestDispatcher.forward(req, res);
            }
        }
    }

}
