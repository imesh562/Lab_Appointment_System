package com.imesh.lab.dao;

import com.imesh.lab.models.UserModel;

import java.sql.SQLException;

public interface AuthenticationDao {

    public boolean registerCustomer(UserModel userModel) throws SQLException, ClassNotFoundException;
    public int generateCustomerCode() throws SQLException, ClassNotFoundException;

}
