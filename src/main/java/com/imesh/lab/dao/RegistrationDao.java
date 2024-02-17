package com.imesh.lab.dao;

import com.imesh.lab.models.UserModel;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface RegistrationDao {

    public boolean registerCustomer(UserModel userModel) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException;
    public int generateUserCode() throws SQLException, ClassNotFoundException;
    public boolean checkIfUserExists(String email, String mobileNumber) throws SQLException, ClassNotFoundException;

}
