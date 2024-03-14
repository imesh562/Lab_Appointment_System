package com.imesh.lab.services;

import com.imesh.lab.dao.AdminHomeDaoImpl;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.utils.mail.EmailSender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class AdminHomeService {
    private static AdminHomeService adminHomeService;

    public static synchronized AdminHomeService getService() {
        if (adminHomeService == null) {
            adminHomeService = new AdminHomeService();
        }
        return adminHomeService;
    }

    private static EmailSender getEmailSender() {
        return EmailSender.getEmailSender();
    }

    private AdminHomeDaoImpl getAdminHomeDao() {
        return new AdminHomeDaoImpl();
    }

    public CommonMessageModel getCustomerAppointments(String filter) throws ClassNotFoundException, SQLException {
        int filterCode = switch (filter) {
            case "Upcoming" -> 1;
            case "Pending" -> 2;
            case "Completed" -> 3;
            case "Canceled" -> 4;
            default -> 0;
        };
        return new CommonMessageModel("Data retrieved successfully", true, getAdminHomeDao().getCustomerAppointments(filterCode));
    }

    public CommonMessageModel cancelAppointment(int appointmentId, HttpServletRequest req, String email) throws SQLException, ClassNotFoundException {
        boolean isSuccess = getAdminHomeDao().cancelAppointment(appointmentId);
        if (isSuccess) {
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

    public CommonMessageModel confirmPayment(int appointmentId, HttpServletRequest req, String email) throws SQLException, ClassNotFoundException {
        boolean isSuccess = getAdminHomeDao().confirmPayment(appointmentId);
        if (isSuccess) {
            String name = (String) req.getSession().getAttribute("first_name");
            getEmailSender().sendMail(email, "Confirmation of Payment Received",
                    "Dear "+name+",\n" +
                            "\n" +
                            "I hope this email finds you well.\n" +
                            "\n" +
                            "I wanted to inform you that we have successfully received your payment for appointment number : "+appointmentId+". Thank you for your payment.\n" +
                            "\n" +
                            "If you have any questions or concerns regarding this transaction or any other matter, please feel free to reach out to us. We're here to assist you.\n" +
                            "\n" +
                            "Thank you once again for your business.\n" +
                            "\n" +
                            "Best regards,\n" +
                            "ABC Laboratories");
            return new CommonMessageModel("Payment confirmed successfully", true, null);
        } else {
            return new CommonMessageModel("Payment failed", false, null);
        }
    }

    public CommonMessageModel uploadDocument(HttpServletRequest req) throws SQLException, ClassNotFoundException, ServletException, IOException {

        Part filePart = req.getPart("file");
        int appointmentId  = Integer.parseInt(req.getParameter("appointmentId"));
        int customerId  = Integer.parseInt(req.getParameter("customerId"));
        String email  = req.getParameter("customerEmail");
        String newFileName = customerId+"-"+appointmentId+".pdf";

        if (filePart.getSize() > 10 * 1024 * 1024) {
            return new CommonMessageModel("File size exceeds 10MB limit.", false, null);
        }

        InputStream fileContent = filePart.getInputStream();
        String uploadDir = req.getServletContext().getRealPath("test_results");
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        Path filePath = Paths.get(uploadDir, newFileName);
        Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);

        boolean isSuccess = getAdminHomeDao().changeStatus(appointmentId);
        if (isSuccess) {
            String name = (String) req.getSession().getAttribute("first_name");
            getEmailSender().sendMail(email, "Your Lab Test Results Are Ready for Download",
                    "Dear "+name+",\n" +
                            "\n" +
                            "We hope this email finds you well. We're pleased to inform you that your lab test results are now available for download.\n" +
                            "\n" +
                            "You can access your results by logging into our system using your credentials. Once logged in, navigate to the appropriate section to retrieve your test report.\n" +
                            "\n" +
                            "If you encounter any difficulties accessing your results or have any questions, please don't hesitate to reach out to us. We're here to assist you.\n" +
                            "\n" +
                            "Thank you for choosing us for your healthcare needs.\n" +
                            "\n" +
                            "Best regards,\n" +
                            "ABC Laboratories");
            return new CommonMessageModel("File uploaded successfully", true, null);
        } else {
            return new CommonMessageModel("File uploaded failed", false, null);
        }

    }
}
