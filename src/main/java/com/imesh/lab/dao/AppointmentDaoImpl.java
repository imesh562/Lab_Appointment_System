package com.imesh.lab.dao;

import com.imesh.lab.models.AddAppointmentModel;
import com.imesh.lab.models.TestModel;
import com.imesh.lab.utils.database.ConnectionFactory;

import java.sql.*;
import java.text.ParseException;
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
        String query = "SELECT * FROM Appointments WHERE test_id = ?  AND status = 1 AND scheduled_date > CURDATE()";
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
    public int getTestSpecificDayCount(int test_id, Timestamp date) throws SQLException, ClassNotFoundException, ParseException {
        int count = 0;
        Connection connection = getDbConnection();
        String query = "SELECT COUNT(*) AS count FROM Appointments WHERE test_id = ? AND scheduled_date = ? AND status = 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, test_id);
        statement.setTimestamp(2, date);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            count = result.getInt("count");
        }

        statement.close();
        connection.close();
        return count;
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

    @Override
    public TestModel getTestData(int test_id) throws SQLException, ClassNotFoundException {
        TestModel testModel = null;
        Connection connection = getDbConnection();
        String query = "SELECT * FROM Tests WHERE test_id = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, test_id);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            testModel = new TestModel(
                    result.getInt("test_id"),
                    result.getString("test_name"),
                    result.getInt("daily_slot_count"),
                    result.getDouble("price"),
                    result.getInt("time_period"),
                    result.getString("technician")
            );
        }

        statement.close();
        connection.close();
        return testModel;
    }

    @Override
    public int addNewPayment(double amount) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String query = "INSERT INTO Payments (amount, status) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setDouble(1, amount);
        statement.setInt(2, 0);
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        int paymentId = -1;
        if (generatedKeys.next()) {
            paymentId = generatedKeys.getInt(1);
        }
        statement.close();
        connection.close();

        return paymentId;
    }

    @Override
    public int addNewAppointment(AddAppointmentModel addAppointmentModel, Timestamp date, int customer_id, int payment_id, Timestamp scheduleTimestamp) throws SQLException, ClassNotFoundException, ParseException {
        Connection connection = getDbConnection();
        String query = "INSERT INTO Appointments (test_id, scheduled_date, payment_id, status, customer_id, doctor_name, schedule_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setInt(1, addAppointmentModel.getSelectedTestId());
        statement.setTimestamp(2, date);
        statement.setInt(3, payment_id);
        statement.setInt(4, 1);
        statement.setInt(5, customer_id);
        statement.setString(6, addAppointmentModel.getDoctorName());
        statement.setTimestamp(7, scheduleTimestamp);
        int result = statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        int appointmentId = -1;
        if (generatedKeys.next()) {
            appointmentId = generatedKeys.getInt(1);
        }
        statement.close();
        connection.close();

        return appointmentId;
    }
}
