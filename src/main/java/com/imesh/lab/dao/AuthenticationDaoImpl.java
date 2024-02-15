package com.imesh.lab.dao;

import com.imesh.lab.models.UserModel;
import com.imesh.lab.utils.database.ConnectionFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class AuthenticationDaoImpl implements AuthenticationDao {

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        return new ConnectionFactory().getDatabase().getConnection();
    }

    @Override
    public boolean registerCustomer(UserModel userModel) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String query = "INSERT INTO User (id, first_name, last_name, password, email, mobile) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, generateCustomerCode());
        statement.setString(2, userModel.getFirstName());
        statement.setString(3, userModel.getLastName());
        statement.setString(4, BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt()));
        statement.setString(5, userModel.getEMail());
        statement.setString(6, userModel.getPhoneNumber());

        int result = statement.executeUpdate();
        statement.close();
        connection.close();

        return result > 0;
    }

    @Override
    public int generateCustomerCode() throws SQLException, ClassNotFoundException {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        Connection connection = getDbConnection();
        String query = "SELECT COUNT(*) AS count FROM User WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, Integer.parseInt(sb.toString()));
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            int count = result.getInt("count");
            if (count > 0) {
                generateCustomerCode();
            } else {
                return Integer.parseInt(sb.toString());
            }
        }
        return 0;
    }
}
