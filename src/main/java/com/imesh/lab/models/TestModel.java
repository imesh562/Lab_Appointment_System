package com.imesh.lab.models;

public class TestModel {
    final int testId;
    final String testName;
    final int dailySlotCount;
    final double price;

    public TestModel(int testId, String testName, int dailySlotCount, double price) {
        this.testId = testId;
        this.testName = testName;
        this.dailySlotCount = dailySlotCount;
        this.price = price;
    }

    public int getTestId() {
        return testId;
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
