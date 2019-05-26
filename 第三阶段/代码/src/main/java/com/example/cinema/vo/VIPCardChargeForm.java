package com.example.cinema.vo;

/**
 * @author 李莹
 * @date 2019/4/14
 */
public class VIPCardChargeForm {

    /**
     * vip卡id
     */
    private int vipCardId;

    /**
     * 充值金额
     */
    private double payment;

    public int getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(int vipCardId) {
        this.vipCardId = vipCardId;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
