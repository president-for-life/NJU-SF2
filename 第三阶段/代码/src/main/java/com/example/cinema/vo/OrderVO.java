package com.example.cinema.vo;

import com.example.cinema.po.ScheduleItem;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 梁正川
 */
public class OrderVO {

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
    private List<TicketVO> ticketVOList;

    /**
     * 排片
     */
    private ScheduleItem schedule;

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

    public List<TicketVO> getTicketVOList() {
        return ticketVOList;
    }

    public void setTicketVOList(List<TicketVO> ticketVOList) {
        this.ticketVOList = ticketVOList;
    }

    public ScheduleItem getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleItem schedule) {
        this.schedule = schedule;
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
