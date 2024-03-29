package com.imesh.lab.dao;

import com.imesh.lab.models.AppointmentModel;
import com.imesh.lab.utils.database.ConnectionFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerHomeDaoImpl implements CustomerHomeDao{

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        return new ConnectionFactory().getDatabase().getConnection();
    }
    @Override
    public List<AppointmentModel> getCustomerAppointments(int userId, int filterCode) throws SQLException, ClassNotFoundException {
        List<AppointmentModel> appointments = new ArrayList<>();
        Connection connection = getDbConnection();
        PreparedStatement statement = null;
        if(filterCode == 0){
            String query = "SELECT * FROM Appointments JOIN Tests ON Appointments.test_id = Tests.test_id JOIN Payments ON Appointments.payment_id = Payments.payment_id WHERE Appointments.customer_id = ?;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
        } else {
            String query = "SELECT * FROM Appointments JOIN Tests ON Appointments.test_id = Tests.test_id JOIN Payments ON Appointments.payment_id = Payments.payment_id WHERE Appointments.customer_id = ? AND Appointments.status = ?;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, filterCode);
        }
        ResultSet result = statement.executeQuery();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy h:mm a");

        while (result.next()) {
            String statusType = "";
            switch (result.getInt("status")) {
                case 1:
                    statusType = "Upcoming";
                    break;
                case 2:
                    statusType = "Pending";
                    break;
                case 3:
                    statusType = "Completed";
                    break;
                case 4:
                    statusType = "Canceled";
                    break;
                default:
                    statusType = "";
                    break;
            }
            appointments.add(
                    new AppointmentModel(
                            result.getInt("appointment_id"),
                            result.getInt("test_id"),
                            dateFormat.format(result.getTimestamp("created_date")),
                            result.getInt("status"),
                            statusType,
                            result.getInt("customer_id"),
                            null,
                            result.getString("doctor_name"),
                            result.getInt("payment_id"),
                            dateFormat.format(result.getTimestamp("schedule_time")),
                            result.getString("test_name"),
                            result.getDouble("amount")
                    )
            );
        }

        statement.close();
        connection.close();
        return appointments;
    }

    @Override
    public boolean cancelAppointment(int userId, int filterCode) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        String query = "UPDATE Appointments SET status = 4 WHERE customer_id = ? AND appointment_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, userId);
        statement.setInt(2, filterCode);
        int rowsAffected = statement.executeUpdate();

        statement.close();
        connection.close();

        return rowsAffected > 0;
    }
}
