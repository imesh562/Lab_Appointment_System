package com.imesh.lab.models;

public class AppointmentModel {
    final int appointmentId;
    final int testId;
    final String createdDate;
    final int status;
    final String statusType;
    final int customerId;
    final String doctorName;
    final int paymentId;
    final String scheduleTime;
    final String testName;
    final double amount;

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getStatusType() {
        return statusType;
    }

    public int getTestId() {
        return testId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public int getStatus() {
        return status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public String getTestName() {
        return testName;
    }

    public double getAmount() {
        return amount;
    }

    public AppointmentModel(int appointmentId, int testId, String createdDate, int status, String statusType, int customerId, String doctorName, int paymentId, String scheduleTime, String testName, double amount) {
        this.appointmentId = appointmentId;
        this.testId = testId;
        this.createdDate = createdDate;
        this.status = status;
        this.statusType = statusType;
        this.customerId = customerId;
        this.doctorName = doctorName;
        this.paymentId = paymentId;
        this.scheduleTime = scheduleTime;
        this.testName = testName;
        this.amount = amount;
    }
}
