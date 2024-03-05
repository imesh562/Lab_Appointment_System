package com.imesh.lab.dao;

import com.imesh.lab.models.AddAppointmentModel;
import com.imesh.lab.models.TestModel;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public interface AppointmentDao {
    public List<TestModel> getAllLabTests() throws SQLException, ClassNotFoundException;
    public List<Timestamp> getScheduledDates(int test_id) throws SQLException, ClassNotFoundException;
    public int getTestSpecificDayCount(int test_id, Timestamp date) throws SQLException, ClassNotFoundException, ParseException;
    public int getTestLimit(int test_id) throws SQLException, ClassNotFoundException;
    public TestModel getTestData(int test_id) throws SQLException, ClassNotFoundException;
    public int addNewPayment(double addNewPayment) throws SQLException, ClassNotFoundException;
    public int addNewAppointment(AddAppointmentModel addAppointmentModel, Timestamp date, int id, int payment_id) throws SQLException, ClassNotFoundException, ParseException;
}
