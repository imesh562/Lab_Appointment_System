package com.imesh.lab.models;

import com.google.gson.annotations.SerializedName;


public class LoginModel {

    @SerializedName("user_id")
    String userId;

    @SerializedName("password")
    String password;


    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

}