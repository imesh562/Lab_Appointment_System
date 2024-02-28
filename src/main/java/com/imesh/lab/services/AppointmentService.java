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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public CommonMessageModel getAllLabTests() throws ClassNotFoundException, SQLException {
        return new CommonMessageModel("Data retrieved successfully", true, getAppointmentDao().getAllLabTests());
    }
    public CommonMessageModel getDisabledDates(int test_id) throws ClassNotFoundException, SQLException {
        List<String> disabledDates = new ArrayList<>();
        List<Timestamp> dates = getAppointmentDao().getScheduledDates(test_id);
        int limit = getAppointmentDao().getTestLimit(test_id);

        Map<String, Integer> dateCounts = new HashMap<>();
        for (Timestamp timestamp : dates) {
            String date = timestamp.toLocalDateTime().toLocalDate().toString();
            dateCounts.put(date, dateCounts.getOrDefault(date, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : dateCounts.entrySet()) {
            if (entry.getValue() >= limit) {
                disabledDates.add(entry.getKey());
            }
        }

        return new CommonMessageModel("Data retrieved successfully", true, disabledDates);
    }
}
