package com.imesh.lab.services;

import com.imesh.lab.dao.CustomerHomeDao;
import com.imesh.lab.dao.CustomerHomeDaoImpl;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.utils.mail.EmailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;

public class CustomerHomeService {
    private static CustomerHomeService customerHomeService;

    public static synchronized CustomerHomeService getService() {
        if (customerHomeService == null) {
            customerHomeService = new CustomerHomeService();
        }
        return customerHomeService;
    }

    private static EmailSender getEmailSender() {
        return EmailSender.getEmailSender();
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

    public CommonMessageModel cancelAppointment(int userId, int appointmentId, HttpServletRequest req) throws SQLException, ClassNotFoundException {
        boolean isSuccess = getCustomerHomeDao().cancelAppointment(userId, appointmentId);
        if (isSuccess) {
            String email = (String) req.getSession().getAttribute("email");
            String name = (String) req.getSession().getAttribute("first_name");
            getEmailSender().sendMail(email, "Appointment Cancellation - Appointment Number: "+appointmentId,
                    "Dear "+name+",\n" +
                            "\n" +
                            "Your appointment has been canceled. Your appointment number is: "+appointmentId+"\n" +
                            "\n" +
                            "Let us know if you need any info.\n" +
                            "\n" +
                            "Best regards,\n" +
                            "ABC Laboratories");
            return new CommonMessageModel("Appointment canceled successfully", true, null);
        } else {
            return new CommonMessageModel("Appointment cancellation failed", false, null);
        }
    }

    public void downloadDocument(int userId, int appointmentId, HttpServletResponse res, HttpServletRequest req) throws IOException {
        String fileName = userId + "-" + appointmentId + ".pdf";
        String filePath = req.getServletContext().getRealPath("/test_results/") + File.separator + fileName;
        File file = new File(filePath);

        if (file.exists()) {
            res.setContentType("application/pdf");
            res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            try (InputStream inputStream = new FileInputStream(file); OutputStream outputStream = res.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
