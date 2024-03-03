package com.imesh.lab.models;
import java.util.Date;

import com.google.gson.annotations.SerializedName;


public class AddAppointmentModel {

    @SerializedName("selectedDate")
    String selectedDate;

    @SerializedName("doctorName")
    String doctorName;

    @SerializedName("selectedTestId")
    int selectedTestId;


    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
    public String getSelectedDate() {
        return selectedDate;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getDoctorName() {
        return doctorName;
    }

    public void setSelectedTestId(int selectedTestId) {
        this.selectedTestId = selectedTestId;
    }
    public int getSelectedTestId() {
        return selectedTestId;
    }

}