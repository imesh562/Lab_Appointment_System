package com.imesh.lab.services;

import com.google.gson.Gson;
import com.imesh.lab.dao.AppointmentDao;
import com.imesh.lab.dao.AppointmentDaoImpl;
import com.imesh.lab.models.AddAppointmentModel;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.TestModel;
import com.imesh.lab.utils.data_mapper.DataMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public CommonMessageModel addNewAppointment(HttpServletRequest req, DataMapper dataMapper) throws SQLException, ClassNotFoundException, ParseException {
        if(req.getParameter("selectedTestId") == null || !(req.getParameter("selectedTestId").matches("[0-9]+"))){
            return new CommonMessageModel("Please select a valid test", false, null);
        }

        TestModel test = getAppointmentDao().getTestData(Integer.parseInt(req.getParameter("selectedTestId")));
        if(test == null){
            return new CommonMessageModel("Please select a valid test", false, null);
        }

        AddAppointmentModel addAppointmentModel = new Gson().fromJson(dataMapper.mapData(req), AddAppointmentModel.class);
        List<Timestamp> dates = getAppointmentDao().getScheduledDates(addAppointmentModel.getSelectedTestId());
        for (Timestamp timestamp : dates) {
            String date = timestamp.toLocalDateTime().toLocalDate().toString();
            if(date.equals(addAppointmentModel.getSelectedDate())){
                return new CommonMessageModel("Please select a valid date", false, null);
            }
        }

        int paymentId = getAppointmentDao().addNewPayment(test.getPrice());
        if(paymentId == -1){
            return new CommonMessageModel("Failed to create the appointment", false, null);
        }

        int id = (int) req.getSession().getAttribute("id");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = (Date) dateFormat.parse(addAppointmentModel.getSelectedDate());
        Timestamp timestamp = new Timestamp(date.getTime());
        if(!getAppointmentDao().addNewAppointment(addAppointmentModel, timestamp, id, paymentId)){
            return new CommonMessageModel("Failed to create the appointment", false, null);
        }

        ///TODO: Send Email.
        return new CommonMessageModel(null, true, null);
    }
}
