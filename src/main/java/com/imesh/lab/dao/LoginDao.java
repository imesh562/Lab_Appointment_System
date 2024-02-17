package com.imesh.lab.dao;

import com.imesh.lab.models.LoginModel;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface LoginDao {
    public boolean loginUser(LoginModel loginModel, HttpServletRequest req) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException;
}
