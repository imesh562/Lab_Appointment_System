package com.imesh.lab.dao;

import com.imesh.lab.models.AppointmentModel;

import java.sql.SQLException;
import java.util.List;

public interface AdminHomeDao {
    public List<AppointmentModel> getCustomerAppointments(int filterCode) throws SQLException, ClassNotFoundException;
    public boolean cancelAppointment(int appointmentId) throws SQLException, ClassNotFoundException;
}
