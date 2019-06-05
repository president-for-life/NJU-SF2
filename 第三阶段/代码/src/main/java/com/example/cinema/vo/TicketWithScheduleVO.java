package com.example.cinema.vo;

import com.example.cinema.po.ScheduleItem;

import java.sql.Timestamp;

/**
 * @author 李莹
 * @date 2019/4/16
 */
public class TicketWithScheduleVO {

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
     * 排片
     */
    private ScheduleItem schedule;

    /**
     * 列号
     */
    private int columnIndex;

    /**
     * 排号
     */
    private int rowIndex;

    /**
     * 电影票状态
     */
    private String state;

    /**
     * 实际支付金额
     */
    private double actualPayment;

    /**
     * 下单时间
     */
    private Timestamp time;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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

    public ScheduleItem getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleItem schedule) {
        this.schedule = schedule;
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

    public double getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(double actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
