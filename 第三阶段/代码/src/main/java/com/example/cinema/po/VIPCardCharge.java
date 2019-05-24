package com.example.cinema.po;

import java.sql.Timestamp;

/**
 * @author 梁正川
 */
public class VIPCardCharge {

    /**
     * 充值id
     */
    private int id;

    /**
     * vip卡id
     */
    private int vipCardId;

    /**
     * 充值时间
     */
    private Timestamp time;

    /**
     * 充值金额
     */
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(int vipCardId) {
        this.vipCardId = vipCardId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
