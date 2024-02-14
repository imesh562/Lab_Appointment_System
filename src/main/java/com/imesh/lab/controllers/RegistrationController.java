package com.imesh.lab.controllers;

import com.google.gson.Gson;
import com.imesh.lab.models.requests.UserRegistrationRequest;
import com.imesh.lab.services.AuthService;
import com.imesh.lab.utils.data_mapper.DataMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    private static DataMapper getDataMapper() {
        return DataMapper.getDataMapper();
    }

    private static AuthService getAuthService() {
        return AuthService.getService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        switch (req.getParameter("action-type")) {
            case "Register":
                registerUser(req);
                break;
        }
    }

    boolean registerUser(HttpServletRequest req){
        UserRegistrationRequest userData = new Gson().fromJson(getDataMapper().mapData(req), UserRegistrationRequest.class);
        return true;
    }
}
