package com.example.cinema.vo;

import com.example.cinema.po.Consumption;

public class ConsumptionVO {
    /**
     * 用户id
     */
    private int userId;

    /**
     * 历史消费总额
     */
    private double amount;

    public ConsumptionVO(Consumption consumption){
        this.userId=consumption.getUserId();
        this.amount=consumption.getAmount();
    }

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
