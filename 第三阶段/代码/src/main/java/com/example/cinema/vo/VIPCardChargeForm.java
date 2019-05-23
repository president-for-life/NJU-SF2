package com.example.cinema.vo;

/**
 * Created by liying on 2019/4/14.
 */
public class VIPCardChargeForm {

    /**
     * vip卡id
     */
    private int vipCardId;

    /**
     * 充值金额
     */
    private int amount;

    public int getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(int vipCardId) {
        this.vipCardId = vipCardId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
