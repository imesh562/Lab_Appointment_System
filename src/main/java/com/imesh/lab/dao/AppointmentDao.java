package com.imesh.lab.dao;

import com.imesh.lab.models.TestModel;

import java.sql.SQLException;
import java.util.List;

public interface AppointmentDao {
    public List<TestModel> getAllLabTests() throws SQLException, ClassNotFoundException;
}
