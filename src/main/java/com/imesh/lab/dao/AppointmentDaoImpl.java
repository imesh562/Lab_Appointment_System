package com.imesh.lab.dao;

import com.imesh.lab.models.TestModel;
import com.imesh.lab.utils.database.ConnectionFactory;

import java.sql.*;
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

        while (result.next()) {
            tests.add(new TestModel(
                    result.getInt("test_id"),
                    result.getString("test_name"),
                    result.getInt("daily_slot_count"),
                    result.getDouble("price"),
                    result.getInt("time_period"),
                    result.getString("technician")
            ));
        }

        statement.close();
        connection.close();
        return tests;
    }

    @Override
    public List<Timestamp> getScheduledDates(int test_id) throws SQLException, ClassNotFoundException {
        List<Timestamp> dates = new ArrayList<>();
        Connection connection = getDbConnection();
        String query = "SELECT * FROM Appointments WHERE test_id = ? AND scheduled_date > CURDATE()";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, test_id);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            dates.add(result.getTimestamp("scheduled_date"));
        }

        statement.close();
        connection.close();
        return dates;
    }

    @Override
    public int getTestLimit(int test_id) throws SQLException, ClassNotFoundException {
        int limit = 0;
        Connection connection = getDbConnection();
        String query = "SELECT * FROM Tests WHERE test_id = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, test_id);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            limit = result.getInt("daily_slot_count");
        }

        statement.close();
        connection.close();
        return limit;
    }
}
