package com.imesh.lab.services;

import com.google.gson.Gson;
import com.imesh.lab.dao.AppointmentDao;
import com.imesh.lab.dao.AppointmentDaoImpl;
import com.imesh.lab.models.AddAppointmentModel;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.TestModel;
import com.imesh.lab.utils.data_mapper.DataMapper;
import com.imesh.lab.utils.mail.EmailSender;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public CommonMessageModel addNewAppointment(HttpServletRequest req, DataMapper dataMapper, EmailSender emailSender) throws SQLException, ClassNotFoundException, ParseException {
        if(req.getParameter("selectedTestId") == null || !(req.getParameter("selectedTestId").matches("[0-9]+"))){
            return new CommonMessageModel("Please select a valid test", false, null);
        }

        TestModel test = getAppointmentDao().getTestData(Integer.parseInt(req.getParameter("selectedTestId")));
        if(test == null){
            return new CommonMessageModel("Please select a valid test", false, null);
        }

        AddAppointmentModel addAppointmentModel = new Gson().fromJson(dataMapper.mapData(req), AddAppointmentModel.class);
        List<String> dates = (List<String>) getDisabledDates(addAppointmentModel.getSelectedTestId()).getData();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate tempDate = LocalDate.parse(addAppointmentModel.getSelectedDate().replace('/', '-'), inputFormatter);
        String formattedDate = tempDate.format(outputFormatter);
        for (String date : dates) {
            if(date.equals(formattedDate)){
                return new CommonMessageModel("Please select a valid date", false, null);
            }
        }

        int paymentId = getAppointmentDao().addNewPayment(test.getPrice());
        if(paymentId == -1){
            return new CommonMessageModel("Failed to create the appointment", false, null);
        }

        int id = (int) req.getSession().getAttribute("id");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = dateFormat.parse(addAppointmentModel.getSelectedDate());
        Timestamp timestamp = new Timestamp(date.getTime());
        int appointmentId = getAppointmentDao().addNewAppointment(addAppointmentModel, timestamp, id, paymentId);
        if(appointmentId == -1){
            return new CommonMessageModel("Failed to create the appointment", false, null);
        }

        Date selectedDate = dateFormat.parse(addAppointmentModel.getSelectedDate());
        Timestamp selectedTimestamp = new Timestamp(date.getTime());
        int count = getAppointmentDao().getTestSpecificDayCount(addAppointmentModel.getSelectedTestId(), selectedTimestamp);
        int minutes = test.getTimePeriod() * (count - 1);
        if(minutes >= 300){
            minutes = minutes + 60;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, minutes);
        Date newDate = calendar.getTime();

        String email = (String) req.getSession().getAttribute("email");
        String firstName = (String) req.getSession().getAttribute("first_name");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormatter.format(newDate);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
        String timeString = timeFormatter.format(newDate);
        emailSender.sendMail(email, "Confirmation of Your Lab Test Appointment",
                "Dear "+firstName+",\n" +
                        "\n" +
                        "We are pleased to confirm your appointment for a lab test with us. Here are the details of your appointment:\n" +
                        "\n" +
                        "Appointment ID: "+appointmentId+"\n" +
                        "Test Name: "+test.getTestName()+"\n" +
                        "Technician Name: "+test.getTechnician()+"\n" +
                        "Date: "+dateString+"\n" +
                        "Time: "+timeString+"\n" +
                        "We look forward to seeing you at our facility on the scheduled date and time. Please feel free to contact us if you have any questions.\n" +
                        "\n" +
                        "Thank you for choosing ABC Laboratories. We appreciate your trust in us.\n" +
                        "\n" +
                        "Best regards,\n" +
                        "ABC Laboratories");
        return new CommonMessageModel(null, true, null);
    }
}
