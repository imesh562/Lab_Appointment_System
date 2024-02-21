package com.imesh.lab.dao;

import com.imesh.lab.models.TestModel;
import com.imesh.lab.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDaoImpl implements AppointmentDao{

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        return new ConnectionFactory().getDatabase().getConnection();
    }
    @Override
    public List<TestModel> getAllLabTests() throws SQLException, ClassNotFoundException {
        List<TestModel> tests = new ArrayList<>();
        Connection connection = getDbConnection();
        String query = "SELECT * FROM Tests";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            tests.add(new TestModel(
                    result.getInt("test_id"),
                    result.getString("test_name"),
                    result.getInt("daily_slot_count"),
                    result.getDouble("price")
            ));
        }

        statement.close();
        connection.close();
        return tests;
    }
}
