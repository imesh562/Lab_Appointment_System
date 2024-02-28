package com.imesh.lab.dao;

import com.imesh.lab.models.TestModel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface AppointmentDao {
    public List<TestModel> getAllLabTests() throws SQLException, ClassNotFoundException;
    public List<Timestamp> getScheduledDates(int test_id) throws SQLException, ClassNotFoundException;
    public int getTestLimit(int test_id) throws SQLException, ClassNotFoundException;
}
