package com.example.cinema.po;

import com.example.cinema.vo.TicketVO;
import com.example.cinema.vo.TicketWithScheduleVO;

import java.sql.Timestamp;

/**
 * @author 李莹
 * @date 2019/4/16
 */
public class Ticket {

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
     * 电影票状态：
     * 0：支付未完成
     * 1：支付已完成但未出票
     * 2：已失效
     * 3：已出票
     * 4：已退票
     */
    private int state;

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

    public double getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(double actualPayment) {
        this.actualPayment = actualPayment;
    }

    public int getState() {
        return state;
    }

    public String getStateString() {
        String stateString;
        switch (this.getState()) {
            case 0:
                stateString = "支付未完成";
                break;
            case 1:
                stateString = "支付已完成";
                break;
            case 2:
                stateString = "已失效";
                break;
            case 3:
                stateString = "已出票";
                break;
            case 4:
                stateString = "已退票";
                break;
            default:
                stateString = "支付未完成";
        }
        return stateString;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public TicketVO getVO() {
        TicketVO vo = new TicketVO();
        vo.setRowIndex(this.getRowIndex());
        vo.setColumnIndex(this.getColumnIndex());
        vo.setScheduleId(this.getScheduleId());
        vo.setId(this.getId());
        vo.setOrderId(this.getOrderId());
        vo.setUserId(this.getUserId());
        vo.setState(this.getStateString());
        vo.setActualPayment(this.getActualPayment());
        vo.setTime(this.getTime());
        return vo;

    }

    public TicketWithScheduleVO getWithScheduleVO() {
        TicketWithScheduleVO vo = new TicketWithScheduleVO();
        vo.setRowIndex(this.getRowIndex());
        vo.setColumnIndex(this.getColumnIndex());
        vo.setOrderId(this.getOrderId());
        vo.setId(this.getId());
        vo.setUserId(this.getUserId());
        // 需要设置ScheduleItem
        vo.setState(this.getStateString());
        vo.setActualPayment(this.getActualPayment());
        vo.setTime(this.getTime());
        return vo;
    }
}
