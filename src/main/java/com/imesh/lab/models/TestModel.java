package com.imesh.lab.models;

public class TestModel {
    final int testId;
    final String testName;
    final int dailySlotCount;
    final double price;
    final int timePeriod;
    final String technician;

    public TestModel(int testId, String testName, int dailySlotCount, double price, int timePeriod, String technician) {
        this.testId = testId;
        this.testName = testName;
        this.dailySlotCount = dailySlotCount;
        this.price = price;
        this.timePeriod = timePeriod;
        this.technician = technician;
    }

    public int getTestId() {
        return testId;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public String getTechnician() {
        return technician;
    }

    public String getTestName() {
        return testName;
    }

    public int getDailySlotCount() {
        return dailySlotCount;
    }

    public double getPrice() {
        return price;
    }
}
