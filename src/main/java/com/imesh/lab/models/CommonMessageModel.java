package com.imesh.lab.models;

public class CommonMessageModel {
    final String message;
    final boolean isSuccess;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public CommonMessageModel(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }
}
