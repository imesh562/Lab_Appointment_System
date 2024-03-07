package com.imesh.lab.services;

import com.imesh.lab.dao.AppointmentDao;
import com.imesh.lab.dao.AppointmentDaoImpl;
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

    public CommonMessageModel getCustomerAppointments(int user_id) throws ClassNotFoundException, SQLException {
        return new CommonMessageModel("Data retrieved successfully", true, getCustomerHomeDao().getCustomerAppointments(user_id));
    }

}
