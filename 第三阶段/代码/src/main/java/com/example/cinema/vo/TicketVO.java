package com.example.cinema.vo;

import java.sql.Timestamp;

public class TicketVO {

    /**
     * 订单id
     */
    private int orderId;

    /**
     * 电影票id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 排片id
     */
    private int scheduleId;

    /**
     * 列号
     */
    private int columnIndex;

    /**
     * 排号
     */
    private int rowIndex;

    /**
     * 订单状态：
     * 0：支付未完成
     * 1：支付已完成但未出票
     * 2：已失效
     * 3：已出票
     * 4：退票未完成
     * 5：退票已完成
     */
    private String state;

    /**
     * 实际支付金额
     */
    private double actualPayment;

    private Timestamp time;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(double actualPayment) {
        this.actualPayment = actualPayment;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
