package com.imesh.lab.dao;

import com.imesh.lab.models.LoginModel;
import com.imesh.lab.utils.database.ConnectionFactory;
import com.imesh.lab.utils.encrypter.HashPassword;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoImpl implements LoginDao{
    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        return new ConnectionFactory().getDatabase().getConnection();
    }

    private static HashPassword getEncrypter() {
        return HashPassword.getHash();
    }
    @Override
    public boolean loginUser(LoginModel loginModel, HttpServletRequest req) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        System.out.println(getEncrypter().hashPassword(loginModel.getPassword()));
        Connection connection = getDbConnection();
        String query = "SELECT * FROM User WHERE id = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, loginModel.getUserId());
        statement.setString(2, getEncrypter().hashPassword(loginModel.getPassword()));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(45*60);
            session.setAttribute("id", resultSet.getInt("id"));
            session.setAttribute("first_name", resultSet.getString("first_name"));
            session.setAttribute("last_name", resultSet.getString("last_name"));
            session.setAttribute("email", resultSet.getString("e_mail"));
            session.setAttribute("phone_number", resultSet.getString("phone_number"));
            session.setAttribute("user_type", resultSet.getInt("user_type"));
            return true;
        } else {
            return false;
        }
    }
}
