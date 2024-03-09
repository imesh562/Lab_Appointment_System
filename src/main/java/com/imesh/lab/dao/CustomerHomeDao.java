package com.imesh.lab.dao;

import com.imesh.lab.models.AppointmentModel;
import com.imesh.lab.models.CommonMessageModel;

import java.sql.SQLException;
import java.util.List;

public interface CustomerHomeDao {
    public List<AppointmentModel> getCustomerAppointments(int userId, int filterCode) throws SQLException, ClassNotFoundException;
    public boolean cancelAppointment(int userId, int filterCode) throws SQLException, ClassNotFoundException;
}
