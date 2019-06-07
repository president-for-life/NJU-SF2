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
     * 排片
     */
    private ScheduleItem schedule;

    /**
     * 订单内的座位
     */
    private List<SeatVO> seatVOList;

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

    public ScheduleItem getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleItem schedule) {
        this.schedule = schedule;
    }

    public List<SeatVO> getSeatVOList() {
        return seatVOList;
    }

    public void setSeatVOList(List<SeatVO> seatVOList) {
        this.seatVOList = seatVOList;
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
