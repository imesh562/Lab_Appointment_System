package com.imesh.lab.services;

import com.imesh.lab.dao.LoginDao;
import com.imesh.lab.dao.LoginDaoImpl;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.LoginModel;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginService {
    private static LoginService loginService;

    public static synchronized LoginService getService() {
        if (loginService == null) {
            loginService = new LoginService();
        }
        return loginService;
    }

    private LoginDao getLoginDao() {
        return new LoginDaoImpl();
    }

    public CommonMessageModel loginUser(LoginModel loginModel, HttpServletRequest req) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        boolean isSuccess = getLoginDao().loginUser(loginModel, req);
        if(isSuccess){
            return new CommonMessageModel("Login Successfully", true, null);
        } else {
            return new CommonMessageModel("Incorrect Credentials", false, null);
        }
    }
}
