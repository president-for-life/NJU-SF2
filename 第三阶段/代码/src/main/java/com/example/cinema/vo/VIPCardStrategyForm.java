package com.example.cinema.vo;

import com.example.cinema.po.VIPCardStrategy;

public class VIPCardStrategyForm {

    /**
     * 会员卡策略id
     */
    private int id;

    /**
     * 会员卡价格
     */
    private double price;

    /**
     * 会员卡策略描述
     */
    private String description;

    /**
     * 满
     */
    private double targetAmount;

    /**
     * 送
     */
    private double discountAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public VIPCardStrategy getPO() {
        VIPCardStrategy vipCardStrategy = new VIPCardStrategy();
        vipCardStrategy.setId(this.getId());
        vipCardStrategy.setPrice(this.getPrice());
        vipCardStrategy.setDescription(this.getDescription());
        vipCardStrategy.setTargetAmount(this.getTargetAmount());
        vipCardStrategy.setDiscountAmount(this.getDiscountAmount());
        return vipCardStrategy;
    }
}
