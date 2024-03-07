package com.imesh.lab.dao;

import com.imesh.lab.models.AppointmentModel;
import com.imesh.lab.models.TestModel;

import java.sql.SQLException;
import java.util.List;

public interface CustomerHomeDao {
    public List<AppointmentModel> getCustomerAppointments(int user_id) throws SQLException, ClassNotFoundException;
}
