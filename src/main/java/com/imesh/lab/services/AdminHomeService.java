package com.imesh.lab.services;

import com.imesh.lab.dao.AdminHomeDaoImpl;
import com.imesh.lab.models.CommonMessageModel;

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

    public CommonMessageModel cancelAppointment(int appointmentId) throws SQLException, ClassNotFoundException {
        boolean isSuccess = getAdminHomeDao().cancelAppointment(appointmentId);
        if (isSuccess) {
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

    public CommonMessageModel confirmPayment(int appointmentId) throws SQLException, ClassNotFoundException {
        boolean isSuccess = getAdminHomeDao().confirmPayment(appointmentId);
        if (isSuccess) {
            return new CommonMessageModel("Payment confirmed successfully", true, null);
        } else {
            return new CommonMessageModel("Payment failed", false, null);
        }
    }

    public CommonMessageModel uploadDocument(HttpServletRequest req) throws SQLException, ClassNotFoundException, ServletException, IOException {

        Part filePart = req.getPart("file");
        int appointmentId  = Integer.parseInt(req.getParameter("appointmentId"));
        int customerId  = Integer.parseInt(req.getParameter("customerId"));
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
            return new CommonMessageModel("File uploaded successfully", true, null);
        } else {
            return new CommonMessageModel("File uploaded failed", false, null);
        }

    }
}
