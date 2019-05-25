package com.example.cinema.po;


import java.sql.Timestamp;

/**
 * @author 李莹
 * @date 2019/4/14
 */
public class VIPCard {

    // TODO 删除此属性
    public static final double price = 25;

    // TODO 删除此属性
    public static final String description = "满200送30";

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡策略id
     */
    private int strategyId;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡余额
     */
    private double balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(int strategyId) {
        this.strategyId = strategyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
