package com.imesh.lab.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("e_mail")
    private String eMail;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("password")
    private String password;
    @SerializedName("confirm_password")
    private String confirmPassword;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { this.firstName = value; }

    public String getLastName() { return lastName; }
    public void setLastName(String value) { this.lastName = value; }

    public String getEMail() { return eMail; }
    public void setEMail(String value) { this.eMail = value; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String value) { this.phoneNumber = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String value) { this.confirmPassword = value; }
}
