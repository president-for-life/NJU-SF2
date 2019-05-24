package com.example.cinema.po;


import java.sql.Timestamp;

/**
 * @author 李莹
 * @date 2019/4/14
 */
public class VIPCard {

    public static final double price = 25;

    public static final String description = "满200送30";

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡余额
     */
    private double balance;

    // TODO private double targetAmount;
    // TODO private double discountAmount;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public double calculate(double amount) {
        // TODO
        return (int) (amount / 200) * 30 + amount;
    }
}
