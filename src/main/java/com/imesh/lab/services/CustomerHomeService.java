package com.imesh.lab.services;

import com.imesh.lab.dao.CustomerHomeDao;
import com.imesh.lab.dao.CustomerHomeDaoImpl;
import com.imesh.lab.models.CommonMessageModel;

import java.sql.SQLException;

public class CustomerHomeService {
    private static CustomerHomeService customerHomeService;

    public static synchronized CustomerHomeService getService() {
        if (customerHomeService == null) {
            customerHomeService = new CustomerHomeService();
        }
        return customerHomeService;
    }

    private CustomerHomeDao getCustomerHomeDao() {
        return new CustomerHomeDaoImpl();
    }

    public CommonMessageModel getCustomerAppointments(int userId, String filter) throws ClassNotFoundException, SQLException {
        int filterCode = switch (filter) {
            case "Upcoming" -> 1;
            case "Pending" -> 2;
            case "Completed" -> 3;
            case "Canceled" -> 4;
            default -> 0;
        };
        return new CommonMessageModel("Data retrieved successfully", true, getCustomerHomeDao().getCustomerAppointments(userId, filterCode));
    }

    public CommonMessageModel cancelAppointment(int userId, int appointmentId) throws SQLException, ClassNotFoundException {
        boolean isSuccess = getCustomerHomeDao().cancelAppointment(userId, appointmentId);
        if (isSuccess) {
            return new CommonMessageModel("Appointment canceled successfully", true, null);
        } else {
            return new CommonMessageModel("Appointment cancellation failed", false, null);
        }
    }
}
