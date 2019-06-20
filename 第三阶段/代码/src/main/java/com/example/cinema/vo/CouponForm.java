package com.example.cinema.vo;

import com.example.cinema.po.Coupon;

import java.sql.Timestamp;

/**
 * @author 李莹
 * @date 2019/4/16
 */
public class CouponForm {
    /**
     * 优惠券id
     */
    private int id;

    /**
     * 优惠券描述
     */
    private String description;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券使用门槛
     */
    private double targetAmount;

    /**
     * 优惠券优惠金额
     */
    private double discountAmount;

    /**
     * 可用时间
     */
    private Timestamp startTime;

    /**
     * 失效时间
     */
    private Timestamp endTime;

    public CouponForm() {}

    public CouponForm(Coupon coupon){
        this.description=coupon.getDescription();
        this.discountAmount=coupon.getDiscountAmount();
        this.name=coupon.getName();
        this.startTime=coupon.getStartTime();
        this.endTime=coupon.getEndTime();
        this.targetAmount=coupon.getTargetAmount();
        this.id=coupon.getId();
    }


    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
