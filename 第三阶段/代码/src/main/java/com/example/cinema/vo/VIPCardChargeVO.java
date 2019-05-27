package com.example.cinema.vo;

import com.example.cinema.po.VIPCardCharge;

import java.sql.Timestamp;

/**
 * @author 梁正川
 * @date 2019/5/26
 */
public class VIPCardChargeVO {

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
    private double payment;

    /**
     * 实际充入金额
     */
    private double amount;

    public VIPCardChargeVO(VIPCardCharge vipCardCharge){
        this.vipCardId=vipCardCharge.getVipCardId();
        this.time=vipCardCharge.getTime();
        this.payment=vipCardCharge.getPayment();
        this.amount=vipCardCharge.getAmount();
    }
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

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
