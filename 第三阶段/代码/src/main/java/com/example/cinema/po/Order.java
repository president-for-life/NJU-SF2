package com.example.cinema.po;

import com.example.cinema.vo.TicketVO;

import java.sql.Timestamp;
import java.util.List;

public class Order {

    /**
     * 订单id
     */
    private int orderId;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 订单内的所有电影票
     */
    private List<Ticket> ticketList;

    /**
     * 排片
     */
    private int scheduleId;

    /**
     * 下单时间
     */
    private Timestamp time;

    /**
     * 实际支付金额
     */
    private double actualPayment;

    /**
     * 订单状态：
     * 0：支付未完成
     * 1：支付已完成但未出票
     * 2：已失效
     * 3：已出票
     * 4：已退票
     */
    private String state;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
