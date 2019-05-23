package com.example.cinema.po;

public class Consumption {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 历史消费总额
     */
    private double amount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
