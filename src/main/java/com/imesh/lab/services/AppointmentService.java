package com.imesh.lab.services;

import com.imesh.lab.dao.AppointmentDao;
import com.imesh.lab.dao.AppointmentDaoImpl;
import com.imesh.lab.dao.LoginDao;
import com.imesh.lab.dao.LoginDaoImpl;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.LoginModel;
import com.imesh.lab.models.TestModel;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class AppointmentService {
    private static AppointmentService appointmentService;

    public static synchronized AppointmentService getService() {
        if (appointmentService == null) {
            appointmentService = new AppointmentService();
        }
        return appointmentService;
    }

    private AppointmentDao getAppointmentDao() {
        return new AppointmentDaoImpl();
    }

    public List<TestModel> getAllLabTests() throws ClassNotFoundException, SQLException {
        return getAppointmentDao().getAllLabTests();
    }
}
