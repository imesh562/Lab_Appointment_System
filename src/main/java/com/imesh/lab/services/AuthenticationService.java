package com.imesh.lab.services;

import com.imesh.lab.dao.AuthenticationDao;
import com.imesh.lab.dao.AuthenticationDaoImpl;
import com.imesh.lab.models.UserModel;

import java.sql.SQLException;

public class AuthenticationService {
    private static AuthenticationService authenticationService;

    public static synchronized AuthenticationService getService() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService();
        }
        return authenticationService;
    }

    private AuthenticationDao getAuthenticationDao() {
        return new AuthenticationDaoImpl();
    }

    public boolean registerUser(UserModel userModel) throws ClassNotFoundException, SQLException {
        return getAuthenticationDao().registerCustomer(userModel);
    }

}
