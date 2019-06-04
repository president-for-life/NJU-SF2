package com.example.cinema.vo;

import com.example.cinema.po.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李莹
 * @date 2019/4/16
 */
public class OrderForm {

    /**
     * 订单id
     */
    private int orderId;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 排片id
     */
    private int scheduleId;

    /**
     * 用户选择的座位
     */
    private List<SeatForm> seats;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void generateOrderId() {
        this.orderId = 0;
    }

    public List<SeatForm> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatForm> seats) {
        this.seats = seats;
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

    public List<Ticket> getTicketPOs() {
        List<Ticket> tickets = new ArrayList<>();
        for (SeatForm seat : this.getSeats()) {
            Ticket ticket = new Ticket();

            ticket.setOrderId(this.getOrderId());
            ticket.setUserId(this.getUserId());
            ticket.setScheduleId(this.getScheduleId());
            ticket.setColumnIndex(seat.getColumnIndex());
            ticket.setRowIndex(seat.getRowIndex());
            ticket.setState(0); // 支付未完成

            tickets.add(ticket);
        }
        return tickets;
    }

}
