package com.imesh.lab.dao;

import com.imesh.lab.models.UserModel;
import com.imesh.lab.utils.database.ConnectionFactory;
import com.imesh.lab.utils.encrypter.HashPassword;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class RegistrationDaoImpl implements RegistrationDao {

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        return new ConnectionFactory().getDatabase().getConnection();
    }

    private static HashPassword getEncrypter() {
        return HashPassword.getHash();
    }

    @Override
    public boolean registerCustomer(UserModel userModel) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        Connection connection = getDbConnection();
        String query = "INSERT INTO User (id, first_name, last_name, password, e_mail, phone_number, user_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, userModel.getId());
        statement.setString(2, userModel.getFirstName());
        statement.setString(3, userModel.getLastName());
        statement.setString(4, getEncrypter().hashPassword(userModel.getPassword()));
        statement.setString(5, userModel.getEMail());
        statement.setString(6, userModel.getPhoneNumber());
        statement.setInt(7, 2);

        int result = statement.executeUpdate();
        statement.close();
        connection.close();

        return result > 0;
    }

    @Override
    public int generateUserCode() throws SQLException, ClassNotFoundException {
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
                generateUserCode();
            } else {
                return Integer.parseInt(sb.toString());
            }
        }

        statement.close();
        connection.close();
        return 0;
    }

    @Override
    public boolean checkIfUserExists(String email, String mobileNumber) throws SQLException, ClassNotFoundException {
       Connection connection = getDbConnection();
        String query = "SELECT COUNT(*) AS count FROM User WHERE e_mail = ? OR phone_number = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, mobileNumber);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            int count = result.getInt("count");
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        }

        statement.close();
        connection.close();
        return false;
    }
}
